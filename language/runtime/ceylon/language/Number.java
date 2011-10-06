package ceylon.language;

import com.redhat.ceylon.compiler.metadata.java.TypeInfo;

public interface Number extends Format {
    
	@TypeInfo(value="ceylon.language.Natural")
    public long getNatural();
	@TypeInfo(value="ceylon.language.Integer")
    public long getInteger();
	@TypeInfo(value="ceylon.language.Float")
    public double getFloat();
	
    public boolean getIntegral();
    public boolean getZero();
    public boolean getUnit();
    
}
