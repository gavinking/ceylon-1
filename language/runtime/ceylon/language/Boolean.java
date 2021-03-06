package ceylon.language;

import com.redhat.ceylon.compiler.java.metadata.CaseTypes;
import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.ValueType;
import com.redhat.ceylon.compiler.java.runtime.model.ReifiedType;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;

@Ceylon(major = 8)
@Class(constructors = true)
@CaseTypes({"ceylon.language::true", "ceylon.language::false"})
@SatisfiedTypes({})
@ValueType
@SharedAnnotation$annotation$
@AbstractAnnotation$annotation$
@NativeAnnotation$annotation$(backends={})
public abstract class Boolean 
        implements ReifiedType, java.io.Serializable {

    private static final long serialVersionUID = -2696784743732343464L;
    
    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(Boolean.class);
    
    @Ignore
    public static Boolean instance(boolean value) {
        return value ? true_.get_() : false_.get_();
    }

    @Ignore
    abstract public boolean booleanValue();
    
    @SharedAnnotation$annotation$
    @StaticAnnotation$annotation$
    @TypeInfo("ceylon.language::Boolean|ceylon.language::ParseException")
    public static java.lang.Object parse(
            @Name("string") java.lang.String string) {
        Boolean bool = parseBoolean_.parseBoolean(string);
        return bool!=null ? bool : 
                new ParseException("illegal format for Boolean");
    }

    @SharedAnnotation$annotation$
    public Boolean(){}

    @Ignore
    public static java.lang.String toString(boolean value) {
        return value ? "true" : "false";
    }
    
    @Ignore
    public static boolean equals(boolean value, java.lang.Object that) {
        if (that instanceof Boolean) {
            return value == ((Boolean) that).booleanValue();
        }
        else {
            return false;
        }
    }

    @Override
    @Ignore
    public int hashCode() {
        return hashCode(booleanValue());
    }

    @Ignore
    public static int hashCode(boolean value) {
        return value ? 1231 : 1237;
    }

    @Override
    @Ignore
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }
    
}
