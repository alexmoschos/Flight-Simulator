public class SpeedLimitException extends Throwable {
    int id;
    public SpeedLimitException(int id){
        this.id = id;
    }
}
