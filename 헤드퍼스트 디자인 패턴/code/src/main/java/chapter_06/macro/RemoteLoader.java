package chapter_06.macro;

import chapter_06.advance.*;


public class RemoteLoader {

    public static void main(String[] args) {
        Light light = new Light("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Hottub hottub = new Hottub();

        LightOnCommand lightOn = new LightOnCommand(light);
        StereoOnWithCDCommand stereoOnWithCDCommand = new StereoOnWithCDCommand(stereo);
        HottubOnCommand hottubOnCommand = new HottubOnCommand(hottub);

        Command[] partyOn = {lightOn, stereoOnWithCDCommand, hottubOnCommand};

        MacroCommand macroCommand = new MacroCommand(partyOn);
        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(0, macroCommand, null);
        remoteControl.onButtonWasPushed(0);
    }
}
