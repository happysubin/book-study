package chapter_6.ceiling;



public class CeilingFanOffCommand implements Command {

    private CeilingFan ceilingFan;
    private int prevSpeed; //상태지역 변수로 선풍기의 속도를 저장

    public CeilingFanOffCommand(CeilingFan ceilingFan) {
        this.ceilingFan = ceilingFan;
    }

    @Override
    public void execute() {
        prevSpeed = ceilingFan.getSpeed();
        ceilingFan.off();;
    }

    @Override
    public void undo() {
        if(prevSpeed == CeilingFan.HIGH){
            ceilingFan.high();
        }
        else if(prevSpeed == CeilingFan.MEDIUM){
            ceilingFan.medium();
        }
        else if(prevSpeed == CeilingFan.LOW){
            ceilingFan.low();
        }
        else if(prevSpeed == CeilingFan.OFF){
            ceilingFan.off();
        }
    }
}
