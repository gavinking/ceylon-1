package com.redhat.ceylon.cmr.api;

public class ModuleVersionQuery extends ModuleQuery {

    private String version;

    public ModuleVersionQuery(String name, String version, Type type) {
        super(null, name, type);
        this.version = version;
    }

    public ModuleVersionQuery(String namespace, String name, String version, Type type) {
        super(namespace, name, type);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ModuleVersionQuery[ns=" + namespace + ",name=" + name + ",version=" + version + ",type=" + type + "]";
    }
}
