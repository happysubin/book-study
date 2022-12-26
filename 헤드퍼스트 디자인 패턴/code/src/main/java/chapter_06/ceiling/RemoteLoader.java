package chapter_06.ceiling;

public class RemoteLoader {

    public static void main(String[] args) {
        RemoteControlWithUndo remoteControlWithUndo = new RemoteControlWithUndo();

        CeilingFan ceilingFan = new CeilingFan("Living Room");

        CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHighCommand = new CeilingFanHighCommand(ceilingFan);
        CeilingFanOffCommand offCommand = new CeilingFanOffCommand(ceilingFan);

        remoteControlWithUndo.setCommand(0, ceilingFanMedium, offCommand);
        remoteControlWithUndo.setCommand(1, ceilingFanHighCommand, offCommand);

        remoteControlWithUndo.onButtonWasPushed(0);
        remoteControlWithUndo.offButtonWasPushed(0);
        System.out.println(remoteControlWithUndo);
        remoteControlWithUndo.undoButtonWasPushed();

        remoteControlWithUndo.onButtonWasPushed(1);
        System.out.println(remoteControlWithUndo);
        remoteControlWithUndo.undoButtonWasPushed();

    }
}
