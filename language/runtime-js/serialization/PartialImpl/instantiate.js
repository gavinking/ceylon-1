function instantiate(){
  var classModel=this.clazz;
  if (!classModel) {
    throw DeserializationException("no class specified for instance with id " + this.id);
  }
  var outer,outerClass;
  if (is$(classModel,{t:AppliedClass$jsint})) {
    outer=null;
    outerClass=null;
  } else if (is$(classModel,{t:AppliedMemberClass$jsint})) {
    console.log("TODO! outer para " + classModel.string);
  } else {
    throw AssertionError("unexpected class model " + (classModel&&classModel.string||"NULL"));
  }
  this.instance_=classModel.tipo.inst$$(classModel);
}