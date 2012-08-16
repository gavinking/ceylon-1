function initType(a,b,c,d,e,f,g,h,i,j,k,l);//IGNORE
function String$(x){};//IGNORE
function Character(x){};//IGNORE
function inheritProto(a,b,c,d,e,f,g);//IGNORE
function Exception$(x){};//IGNORE
var Object$,Castable,Integral,Numeric,Exponentiable,Scalar,equal,smaller,larger,exports;//IGNORE

function JSNumber(value) { return Number(value); }
initExistingType(JSNumber, Number, 'ceylon.language.JSNumber');
var origNumToString = Number.prototype.toString;
inheritProtoI(JSNumber, Object$, Scalar, Castable, Integral, Exponentiable);

function Integer(value) { return Number(value); }
initTypeProto(Integer, 'ceylon.language.Integer', Object$, Scalar, Castable,
        Integral, Exponentiable);

function Float(value) {
    var that = new Number(value);
    that.$float = true;
    return that;
}
initTypeProto(Float, 'ceylon.language.Float', Object$, Scalar, Castable, Exponentiable);

var JSNum$proto = Number.prototype;
JSNum$proto.getT$all$ = function() {
    return (this.$float ? Float : Integer).$$.T$all;
}
JSNum$proto.getT$name$ = function() {
    return (this.$float ? Float : Integer).$$.T$name;
}
JSNum$proto.toString = origNumToString;
JSNum$proto.getString = function() { return String$(this.toString()) }
JSNum$proto.plus = function(other) {
    return (this.$float||other.$float) ? Float(this+other) : (this+other);
}
JSNum$proto.minus = function(other) {
    return (this.$float||other.$float) ? Float(this-other) : (this-other);
}
JSNum$proto.times = function(other) {
    return (this.$float||other.$float) ? Float(this*other) : (this*other);
}
JSNum$proto.divided = function(other) {
    if (this.$float||other.$float) { return Float(this/other); }
    if (other == 0) {
        throw Exception(String$("Division by Zero"));
    }
    return (this/other)|0;
}
JSNum$proto.remainder = function(other) { return this%other; }
JSNum$proto.power = function(exp) {
    if (this.$float||exp.$float) { return Float(Math.pow(this, exp)); }
    if (exp<0 && this!=1 && this!=-1) {
        throw Exception(String$("Negative exponent"));
    }
    return Math.pow(this, exp)|0;
}
JSNum$proto.getNegativeValue = function() {
    return this.$float ? Float(-this) : -this;
}
JSNum$proto.getPositiveValue = function() {
    return this.$float ? this : this.valueOf();
}
JSNum$proto.equals = function(other) { return other==this.valueOf(); }
JSNum$proto.compare = function(other) {
    var value = this.valueOf();
    return value==other ? equal : (value<other ? smaller:larger);
}
JSNum$proto.getFloat = function() { return Float(this.valueOf()); }
JSNum$proto.getInteger = function() { return this|0; }
JSNum$proto.getCharacter = function() { return Character(this.valueOf()); }
JSNum$proto.getSuccessor = function() { return this+1 }
JSNum$proto.getPredecessor = function() { return this-1 }
JSNum$proto.getUnit = function() { return this == 1 }
JSNum$proto.getZero = function() { return this == 0 }
JSNum$proto.getFractionalPart = function() {
    if (!this.$float) { return 0; }
    return Float(this - (this>=0 ? Math.floor(this) : Math.ceil(this)));
}
JSNum$proto.getWholePart = function() {
    if (!this.$float) { return this.valueOf(); }
    return Float(this>=0 ? Math.floor(this) : Math.ceil(this));
}
JSNum$proto.getSign = function() { return this > 0 ? 1 : this < 0 ? -1 : 0; }
JSNum$proto.getHash = function() {
    return this.$float ? String$(this.toPrecision()).getHash() : this.valueOf();
}
JSNum$proto.distanceFrom = function(other) {
    return (this.$float ? this.getWholePart() : this) - other;
}

function $parseInteger(s) {
    //xkcd.com/208/
    if ((s.indexOf('_') >= 0 ? s.match(/^[+-]?\d{1,3}(_\d{3})+[kMGPT]?$/g) : s.match(/^[+-]?\d+[kMGPT]?$/g)) === null) {
        return null;
    }
    s = s.replace("_", "");
    var mag = null;
    if (s.match(/[kMGTP]$/g) !== null) {
        mag = s[s.length-1];
        s = s.slice(0,-1);
    }
    var i = parseInt(s);
    var factor=1;
    switch(mag) {
        case 'P':factor*=1000;
        case 'T':factor*=1000;
        case 'G':factor*=1000;
        case 'M':factor*=1000;
        case 'k':factor*=1000;
    }
    return isNaN(i) ? null : i*factor;
}
function $parseFloat(s) { return Float(parseFloat(s)); }

JSNum$proto.getUndefined = function() { return isNaN(this); }
JSNum$proto.getFinite = function() { return this!=Infinity && this!=-Infinity && !isNaN(this); }
JSNum$proto.getInfinite = function() { return this==Infinity || this==-Infinity; }
JSNum$proto.getStrictlyPositive = function() { return this>0 || (this==0 && (1/this==Infinity)); }
JSNum$proto.getStrictlyNegative = function() { return this<0 || (this==0 && (1/this==-Infinity)); }

var $infinity = Float(Infinity);
function getInfinity() { return $infinity; }
//function getNegativeInfinity() { return Float(-Infinity); }

exports.Integer=Integer;
exports.Float=Float;
exports.getInfinity=getInfinity;
exports.parseInteger=$parseInteger;
exports.parseFloat=$parseFloat;
