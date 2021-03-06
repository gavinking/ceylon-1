package com.redhat.ceylon.compiler.java.runtime.metamodel.decl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import ceylon.language.Anything;
import ceylon.language.meta.declaration.OpenType;
import ceylon.language.meta.declaration.SetterDeclaration;
import ceylon.language.meta.declaration.ValueDeclaration$impl;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.Name;
import com.redhat.ceylon.compiler.java.metadata.Nullable;
import com.redhat.ceylon.compiler.java.metadata.TypeInfo;
import com.redhat.ceylon.compiler.java.metadata.TypeParameter;
import com.redhat.ceylon.compiler.java.metadata.TypeParameters;
import com.redhat.ceylon.compiler.java.metadata.Variance;
import com.redhat.ceylon.compiler.java.runtime.metamodel.AnnotationBearing;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Metamodel;
import com.redhat.ceylon.compiler.java.runtime.metamodel.Reflection;
import com.redhat.ceylon.compiler.java.runtime.metamodel.meta.AttributeImpl;
import com.redhat.ceylon.compiler.java.runtime.metamodel.meta.ValueImpl;
import com.redhat.ceylon.compiler.java.runtime.model.TypeDescriptor;
import com.redhat.ceylon.model.loader.NamingBase;
import com.redhat.ceylon.model.typechecker.model.Parameter;
import com.redhat.ceylon.model.typechecker.model.Type;
import com.redhat.ceylon.model.typechecker.model.Scope;
import com.redhat.ceylon.model.typechecker.model.TypeDeclaration;
import com.redhat.ceylon.model.typechecker.model.Value;

@Ceylon(major = 8)
@com.redhat.ceylon.compiler.java.metadata.Class
public class ValueDeclarationImpl 
    extends FunctionOrValueDeclarationImpl
    implements ceylon.language.meta.declaration.ValueDeclaration, AnnotationBearing {

    @Ignore
    public final static TypeDescriptor $TypeDescriptor$ = TypeDescriptor.klass(ValueDeclarationImpl.class);
    
    private OpenType type;

    private SetterDeclarationImpl setter;

    public ValueDeclarationImpl(com.redhat.ceylon.model.typechecker.model.Value declaration) {
        super(declaration);

        this.type = Metamodel.getMetamodel(declaration.getType());
    }

    @Override
    @Ignore
    public ValueDeclaration$impl $ceylon$language$meta$declaration$ValueDeclaration$impl() {
        return null;
    }
    
//    @Override
//    @Ignore
//    public GettableDeclaration$impl $ceylon$language$meta$declaration$GettableDeclaration$impl() {
//        return null;
//    }

    @Override
    public boolean getLate(){
        return ((com.redhat.ceylon.model.typechecker.model.TypedDeclaration) declaration).isLate();
    }
    
    @Override
    public boolean getVariable(){
        return ((com.redhat.ceylon.model.typechecker.model.TypedDeclaration) declaration).isVariable();
    }
    
    
    @Override
    public boolean getObjectValue(){
        return type instanceof ceylon.language.meta.declaration.OpenClassType
                && ((ceylon.language.meta.declaration.OpenClassType) type).getDeclaration().getAnonymous();
    }
    
    @TypeInfo("ceylon.language.meta.declaration::ClassDeclaration|ceylon.language::Null")
    @Override
    public ceylon.language.meta.declaration.ClassDeclaration getObjectClass(){
        if(type instanceof ceylon.language.meta.declaration.OpenClassType){
            ceylon.language.meta.declaration.OpenClassType decl = (ceylon.language.meta.declaration.OpenClassType)type;
            if(decl.getDeclaration().getAnonymous())
                return decl.getDeclaration();
        }
        return null;
    }

    @Override
    @TypeInfo(value="ceylon.language.meta.model::Value<Get,Set>", erased=true)
    @TypeParameters({
        @TypeParameter("Get"),
        @TypeParameter(value="Set", defaultValue="ceylon.language::Nothing"),
    })
    public <Get, Set> ceylon.language.meta.model.Value<Get,Set> apply(@Ignore TypeDescriptor $reifiedGet,
                                                                      @Ignore TypeDescriptor $reifiedSet){
        if(!getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a member declaration with no container type: use memberApply");
        com.redhat.ceylon.model.typechecker.model.Value modelDecl = (com.redhat.ceylon.model.typechecker.model.Value)declaration;
        com.redhat.ceylon.model.typechecker.model.TypedReference typedReference = modelDecl.appliedTypedReference(null, Collections.<Type>emptyList());

        com.redhat.ceylon.model.typechecker.model.Type getType = typedReference.getType();
        TypeDescriptor reifiedGet = Metamodel.getTypeDescriptorForProducedType(getType);
        // immutable values have Set=Nothing
        com.redhat.ceylon.model.typechecker.model.Type setType = getVariable() ? 
                getType : modelDecl.getUnit().getNothingType();
        TypeDescriptor reifiedSet = getVariable() ? reifiedGet : TypeDescriptor.NothingType;
        
        Metamodel.checkReifiedTypeArgument("apply", "Value<$1,$2>", 
                Variance.OUT, getType, $reifiedGet,
                Variance.IN, setType, $reifiedSet);
        return new ValueImpl<Get,Set>(reifiedGet, reifiedSet, this, typedReference, null, null);
    }

    @TypeInfo("ceylon.language.meta.model::Attribute<Container,Get,Set>")
    @TypeParameters({
        @TypeParameter("Container"),
        @TypeParameter("Get"),
        @TypeParameter(value="Set", defaultValue="ceylon.language::Nothing"),
    })
    @Override
    public <Container, Get, Set>
        ceylon.language.meta.model.Attribute<Container, Get, Set> memberApply(
                @Ignore TypeDescriptor $reifiedContainer,
                @Ignore TypeDescriptor $reifiedGet,
                @Ignore TypeDescriptor $reifiedSet,
                @Name("containerType") ceylon.language.meta.model.Type<? extends Object> containerType){
        if(getToplevel())
            throw new ceylon.language.meta.model.TypeApplicationException("Cannot apply a toplevel declaration to a container type: use apply");
        Type qualifyingType = Metamodel.getModel(containerType);
        Metamodel.checkQualifyingType(qualifyingType, declaration);
        com.redhat.ceylon.model.typechecker.model.Value modelDecl = (com.redhat.ceylon.model.typechecker.model.Value)declaration;
        // find the proper qualifying type
        Type memberQualifyingType = qualifyingType.getSupertype((TypeDeclaration) modelDecl.getContainer());
        com.redhat.ceylon.model.typechecker.model.TypedReference typedReference = modelDecl.appliedTypedReference(memberQualifyingType, Collections.<Type>emptyList());
        TypeDescriptor reifiedContainer = Metamodel.getTypeDescriptorForProducedType(qualifyingType);
        
        com.redhat.ceylon.model.typechecker.model.Type getType = typedReference.getType();
        TypeDescriptor reifiedGet = Metamodel.getTypeDescriptorForProducedType(getType);
        // immutable values have Set=Nothing
        com.redhat.ceylon.model.typechecker.model.Type setType = getVariable() ? 
                getType : modelDecl.getUnit().getNothingType();
        TypeDescriptor reifiedSet = getVariable() ? reifiedGet : TypeDescriptor.NothingType;
        
        Metamodel.checkReifiedTypeArgument("memberApply", "Attribute<$1,$2,$3>", 
                Variance.IN, memberQualifyingType, $reifiedContainer,
                Variance.OUT, getType, $reifiedGet,
                Variance.IN, setType, $reifiedSet);
        return new AttributeImpl<Container,Get,Set>(reifiedContainer, reifiedGet, reifiedSet, this, typedReference, containerType);
    }

    @Override
    @TypeInfo("ceylon.language.meta.declaration::OpenType")
    public OpenType getOpenType() {
        return type;
    }

    @TypeInfo("ceylon.language::Anything")
    @Override
    public Object get(){
        return apply(Anything.$TypeDescriptor$, TypeDescriptor.NothingType).get();
    }

    @TypeInfo("ceylon.language::Anything")
    @Override
    public Object memberGet(@Name("container") @TypeInfo("ceylon.language::Object") Object container){
        ceylon.language.meta.model.Type<?> containerType = Metamodel.getAppliedMetamodel(Metamodel.getTypeDescriptor(container));
        return memberApply(TypeDescriptor.NothingType, Anything.$TypeDescriptor$, TypeDescriptor.NothingType, containerType).bind(container).get();
    }

    @Override
    public int hashCode() {
        return Metamodel.hashCode(this, "value");
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof ValueDeclarationImpl == false)
            return false;
        return Metamodel.equalsForSameType(this, (ValueDeclarationImpl)obj);
    }

    @TypeInfo("ceylon.language::Anything")
    @Override
    public Object set(@TypeInfo("ceylon.language::Anything") @Name("newValue") @Nullable Object newValue){
        return apply(Anything.$TypeDescriptor$, TypeDescriptor.NothingType).$setIfAssignable(newValue);
    }

    @TypeInfo("ceylon.language::Anything")
    @Override
    public Object memberSet(@Name("container") @TypeInfo("ceylon.language::Object") Object container,
            @TypeInfo("ceylon.language::Anything") @Name("newValue") @Nullable Object newValue){
        ceylon.language.meta.model.Type<?> containerType = Metamodel.getAppliedMetamodel(Metamodel.getTypeDescriptor(container));
        return memberApply(TypeDescriptor.NothingType, Anything.$TypeDescriptor$, TypeDescriptor.NothingType, containerType).bind(container).$setIfAssignable(newValue);
    }

    @Override
    public String toString() {
        return "value "+super.toString();
    }

    @TypeInfo("ceylon.language.meta.declaration::SetterDeclaration|ceylon.language::Null")
    @Override
    public SetterDeclaration getSetter() {
        if(setter == null && ((com.redhat.ceylon.model.typechecker.model.Value)declaration).getSetter() != null){
            synchronized(Metamodel.getLock()){
                if(setter == null){
                    // must be deferred because getter/setter refer to one another
                    com.redhat.ceylon.model.typechecker.model.Setter setterModel = ((com.redhat.ceylon.model.typechecker.model.Value)declaration).getSetter();
                    if(setterModel != null)
                        this.setter = (SetterDeclarationImpl) Metamodel.getOrCreateMetamodel(setterModel);
                }
            }
        }
        return setter;
    }
    
    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return $TypeDescriptor$;
    }

    @Override
    @Ignore
    public java.lang.annotation.Annotation[] $getJavaAnnotations$() {
        Class<?> javaClass = Metamodel.getJavaClass(declaration);
        ArrayList<java.lang.annotation.Annotation> result = new ArrayList<>();
        HashSet<Class<? extends java.lang.annotation.Annotation>> cls = new HashSet<>();
        if(javaClass != null){
            // FIXME: pretty sure this doesn't work with interop and fields
            Method declaredGetter = Reflection.getDeclaredGetter(javaClass, NamingBase.getGetterName(declaration));
            if (declaredGetter != null) {
                addToList(result, cls, declaredGetter.getAnnotations());
            }
            if (!((Value)declaration).isTransient()) {
                // TODO only include those which are java annotations 
                Field field  = Reflection.getDeclaredField(javaClass, declaration.getName());
                if (field != null) {
                    Annotation[] fieldAnnos = field.getAnnotations();
                    addToList(result, cls, fieldAnnos);
                }
                
                Method setter = Reflection.getDeclaredSetter(javaClass, NamingBase.getSetterName(declaration.getName()));
                if (setter != null) { 
                    Annotation[] setterAnnos = setter.getAnnotations();
                    addToList(result, cls, setterAnnos);
                }
            }
        
        }
        // one last chance
        if(parameter != null
                && !parameter.getModel().isShared()){
            // get the annotations from the parameter itself
            Annotation[][] parameterAnnotations;
            Scope container = parameter.getModel().getContainer();
            if(container instanceof com.redhat.ceylon.model.typechecker.model.Function) {
                parameterAnnotations = Metamodel.getJavaMethod(
                        (com.redhat.ceylon.model.typechecker.model.Function)container)
                        .getParameterAnnotations();
            } else if(container instanceof com.redhat.ceylon.model.typechecker.model.ClassAlias){
                parameterAnnotations = Reflection.findClassAliasInstantiator(
                        Metamodel.getJavaClass((com.redhat.ceylon.model.typechecker.model.Class)container),
                        (com.redhat.ceylon.model.typechecker.model.ClassAlias)container)
                        .getParameterAnnotations();
            } else if(container instanceof com.redhat.ceylon.model.typechecker.model.Class){
                // FIXME: pretty sure that's wrong because of synthetic params. See ReflectionMethod.getParameters
                parameterAnnotations = Reflection.findConstructor(Metamodel.getJavaClass((com.redhat.ceylon.model.typechecker.model.Class)container)).getParameterAnnotations();
            }else{
                throw Metamodel.newModelError("Unsupported parameter container");
            }
            // now find the right parameter
            List<Parameter> parameters = ((com.redhat.ceylon.model.typechecker.model.Functional)container).getFirstParameterList().getParameters();
            int index = parameters.indexOf(parameter);
            if(index == -1)
                throw Metamodel.newModelError("Parameter "+parameter+" not found in container "+parameter.getModel().getContainer());
            if(index >= parameterAnnotations.length)
                throw Metamodel.newModelError("Parameter "+parameter+" index is greater than JVM parameters for "+parameter.getModel().getContainer());
            addToList(result, cls, parameterAnnotations[index]);
        }
        // nope
        return result.toArray(new java.lang.annotation.Annotation[result.size()]);
    }

    /**
     * Since Ceylon 1.2.3 annotations might be multiply transformed to both
     * a getter and a parameter. To avoid adding both the Java annotations 
     * to the list, only add the annotation if we've not already added an 
     * annotation of the same type.
     */
    protected void addToList(ArrayList<java.lang.annotation.Annotation> result,
            HashSet<Class<? extends java.lang.annotation.Annotation>> cls, Annotation[] annotations) {
        for (java.lang.annotation.Annotation a : annotations) {
            Class<? extends Annotation> at = a.annotationType();
            if (!cls.contains(at)) {
                cls.add(at);
                result.add(a);
            }
        }
    }
    
    @Override
    @Ignore
    public boolean $isAnnotated$(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        Class<?> javaClass = Metamodel.getJavaClass(declaration);
        if(javaClass != null){
            // FIXME: pretty sure this doesn't work with interop and fields
            Method declaredGetter = Reflection.getDeclaredGetter(javaClass, NamingBase.getGetterName(declaration));
            if(declaredGetter != null) 
                return declaredGetter.isAnnotationPresent(annotationType);
        }
        // one last chance
        if(parameter != null
                && !parameter.getModel().isShared()){
            for (java.lang.annotation.Annotation a : $getJavaAnnotations$()) {
                if (a.annotationType().equals(annotationType)) {
                    return true;
                }
            }
            return false;
        }
        // nope
        return false;
    }
    
    @Override
    public <AnnotationType extends java.lang.annotation.Annotation> boolean annotated(TypeDescriptor reifed$AnnotationType) {
        return Metamodel.isAnnotated(reifed$AnnotationType, this);
    }
}
