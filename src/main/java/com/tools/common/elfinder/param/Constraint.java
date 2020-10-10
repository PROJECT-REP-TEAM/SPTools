package com.tools.common.elfinder.param;


import lombok.Data;

import java.io.Serializable;

@Data
public class Constraint implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean locked;
    private boolean readable;
    private boolean writable;

}
