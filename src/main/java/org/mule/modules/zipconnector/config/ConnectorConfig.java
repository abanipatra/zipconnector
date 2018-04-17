package org.mule.modules.zipconnector.config;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.param.Default;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

    @Configurable
    @Default("false")
    private boolean multiFilePayload;

    @Configurable
    @Default("")
    private String additionalContentVariableNames;
    
    @Configurable
    @Default("")
    private String fileNames;

	public boolean isMultiFilePayload() {
		return multiFilePayload;
	}

	public void setMultiFilePayload(boolean multiFilePayload) {
		this.multiFilePayload = multiFilePayload;
	}

	public String getAdditionalContentVariableNames() {
		return additionalContentVariableNames;
	}

	public void setAdditionalContentVariableNames(String additionalContentVariableNames) {
		this.additionalContentVariableNames = additionalContentVariableNames;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

}