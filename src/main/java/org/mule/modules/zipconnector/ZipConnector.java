package org.mule.modules.zipconnector;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.SourceStrategy;
import org.mule.api.annotations.param.Payload;
import org.mule.api.callback.SourceCallback;
import org.mule.api.transport.PropertyScope;
import org.mule.modules.zipconnector.config.ConnectorConfig;

@Connector(name="zipconnector", friendlyName="zipconnector")
public class ZipConnector {
	public static final int DEFAULT_BUFFER_SIZE = 32768;
	public static byte[] MAGIC = { 'P', 'K', 0x3, 0x4 };
	
    @Config
    ConnectorConfig config;

    /**
     * Custom processor
     *
     * @param friend Name to be used to generate a greeting message.
     * @return A greeting message
     */
    @Processor
    public byte[] compressAsZip(@Payload Object payload, MuleMessage message) {
    	try {
			List<byte[]> contents = new ArrayList<>();
			if(StringUtils.isEmpty(config.getFileNames())){
				throw new RuntimeException("Filename must be set for this connector");
			}
			if (config.isMultiFilePayload() && payload instanceof List) {
				contents = (List<byte[]>) payload;
			}else if (payload instanceof byte[]) {
				contents.add((byte[]) payload);
			} else if (payload instanceof InputStream) {
				contents.add(IOUtils.toByteArray((InputStream) payload));
			} else if (payload instanceof String) {
				contents.add(((String) payload).getBytes());
			} else {
				throw new RuntimeException("Payload not supported");
			}
			
			if(StringUtils.isNotEmpty(config.getAdditionalContentVariableNames())){
				String[] contentVariables = config.getAdditionalContentVariableNames().split(",");
				for(int i=0 ; i < contentVariables.length; i++){
					Object additionalData = message.getProperty(contentVariables[i], PropertyScope.INVOCATION);
					if (additionalData instanceof byte[]) {
						contents.add((byte[]) additionalData);
					}else if (additionalData instanceof InputStream) {
						contents.add(IOUtils.toByteArray((InputStream) additionalData));
					} else if (additionalData instanceof String) {
						contents.add(((String) additionalData).getBytes());
					} 
				}
			}
			return compressByteArray(contents);
		} catch (Exception ioex) {
			throw new RuntimeException(ioex);
		}
    }
    
    private byte[] compressByteArray(List<byte[]> contents) throws IOException {
		if (contents == null || contents.size() == 0) {
			throw new RuntimeException("Payload not supported");
		}

		ByteArrayOutputStream baos = null;
		ZipOutputStream zos = null;

		try {
			baos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
			zos = new ZipOutputStream(baos);
			String[] fileNames = config.getFileNames().split(",");
			for(int i =0 ; i< contents.size(); i++){
				zos.putNextEntry(new ZipEntry(fileNames[i]));
				zos.write(contents.get(i), 0, contents.get(i).length);
			}
			zos.finish();
			zos.close();

			byte[] compressedByteArray = baos.toByteArray();

			baos.close();
			return compressedByteArray;
		} catch (IOException ioex) {
			throw ioex;
		} finally {
			IOUtils.closeQuietly(zos);
			IOUtils.closeQuietly(baos);
		}
	}

	private boolean isCompressed(byte[] bytes) throws IOException {
		if ((bytes == null) || (bytes.length < 4)) {
			return false;
		} else {
			for (int i = 0; i < MAGIC.length; i++) {
				if (bytes[i] != MAGIC[i]) {
					return false;
				}
			}
			return true;
		}
	}

    /**
     *  Custom Message Source
     *
     *  @param callback The sourcecallback used to dispatch message to the flow
     *  @throws Exception error produced while processing the payload
     */
    @Source(sourceStrategy = SourceStrategy.POLLING,pollingPeriod=5000)
    public void getNewMessages(SourceCallback callback) throws Exception {
        /*
         * Every 5 the flow using this processor will be called and the payload will be the one defined here.
         * 
         * The PAYLOAD can be anything. In this example a String is used.  
         */
        callback.process("Start working");
    }

    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}