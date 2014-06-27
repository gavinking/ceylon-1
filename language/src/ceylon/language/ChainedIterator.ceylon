"An [[Iterator]] that returns the elements of two
 [[Iterable]]s, as if they were chained together."
see (`function Iterable.chain`)
by ("Enrique Zamudio")
class ChainedIterator<out Element,out Other>
		        ({Element*} first, {Other*} second) 
        satisfies Iterator<Element|Other> {
    
    variable Iterator<Element|Other> iter = first.iterator();
    variable value more = true;
    
    shared actual Element|Other|Finished next() {
        variable Element|Other|Finished e = iter.next();
        if (is Finished f=e) {
            if (more) {
                iter = second.iterator();
                more = false;
                e = iter.next();
            }
        }
        return e;
    }
    
    shared actual String string 
            => "(``first`` chain ``second``).iterator()";
}
