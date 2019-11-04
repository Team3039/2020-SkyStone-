package org.firstinspires.ftc.teamcode;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface Constants {

    double DRIVE_PPR = 1120; //REV HD HEX 40:1
    double WHEEL_DIAMETER = 2; // 2" Mecanum
    double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // pi * diameter
    double PPR_TO_INCHES = WHEEL_CIRCUMFERENCE / DRIVE_PPR; // (circumference / pulses in 1 Revolution) = (x / 1 pulse)
    double COUNTS_PER_INCH = 1120 * (1.0/40) / WHEEL_CIRCUMFERENCE;// this number should be 2.2 counts per inch. We basically have to put the number of inches we want to go multiply it by this number to tell the encoder how many counts it needs to go. I think this math is corect.
    double INCHES_PER_SQUARE = 24; //24" length/width

    //To Be Added
    double NINETY_TURN =1111111111; //Replace w/ real
    double ELEVATOR_LEVEL_ONE = 10101010; // Replace w/ real
    double ELEVATOR_LEVEL_TWO = 181818; // Replace w/ real
    double ELEVATOR_LEVEL_THREE = 1234567; // Replace w/ real
    double CLAMP_FOUNDATION = .90; // Replace w/ real
    double RELEASE_FOUNDATION = .0; //Replave w/ real
}
