package com.tools.common.elfinder.configuration;

import com.tools.common.elfinder.param.Node;
import com.tools.common.elfinder.param.Thumbnail;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix="file-manager")
public class ElfinderConfiguration {

    private Thumbnail thumbnail;
    
    private String command;

    private List<Node> volumes = new ArrayList<>();

    private Long maxUploadSize = -1L;

}
