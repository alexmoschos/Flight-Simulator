public class CrashedException extends Throwable {
    int id;
    //CrashedException will be used to catch flights that crash
    public CrashedException(int id){
        this.id = id;
    }
}
