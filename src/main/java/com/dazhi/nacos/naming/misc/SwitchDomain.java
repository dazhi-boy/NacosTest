package com.dazhi.nacos.naming.misc;

import org.springframework.stereotype.Component;

@Component
public class SwitchDomain {

    private boolean defaultInstanceEphemeral = true;

    public boolean isDefaultInstanceEphemeral() {
        return defaultInstanceEphemeral;
    }

    public void setDefaultInstanceEphemeral(boolean defaultInstanceEphemeral) {
        this.defaultInstanceEphemeral = defaultInstanceEphemeral;
    }
}
