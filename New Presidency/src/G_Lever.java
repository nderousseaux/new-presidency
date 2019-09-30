import java.util.ArrayList;
import java.util.Collection;

public class G_Lever {
    private static Collection<Lever> _levers=new ArrayList<>();

    static void addLever(Lever lever){
        _levers.add(lever);
    }

    static public Collection<Lever> getLevers(){
        return _levers;
    }
}
