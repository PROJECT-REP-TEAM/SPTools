package com.tools.common.elfinder.param;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    private String source;
    private String alias;
    private String path;
    private Boolean isDefault;
    private String locale;
    private Constraint constraint;

}
