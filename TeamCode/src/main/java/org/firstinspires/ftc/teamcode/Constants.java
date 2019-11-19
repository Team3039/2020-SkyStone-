package org.firstinspires.ftc.teamcode;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface Constants {

    //Drivetrain
    double DRIVE_PPR = 134.4 ; //Andymark Neverest 20. This includes gear reduction.
    double WHEEL_DIAMETER = 2; // 2" Mecanum. May have to edit this????
    double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // pi * diameter
    double PPR_TO_INCHES = WHEEL_CIRCUMFERENCE / DRIVE_PPR; // (circumference / pulses in 1 Revolution) = (x  inches / 1 pulse)
    double INCHES_PER_SQUARE = 24; //24" length/width
    double STRAFE_SPEED= .7;

    //Elevator
    double UPPER_LIMIT = 1000;
    double ELEVATOR_GAIN = .95;
    double SLOWED_ELEVATOR_GAIN = .25;

    //Servo Positions
    double CLAMP_FOUNDATION = 1;
    double RELEASE_FOUNDATION = 0;


    //Intake Speeds
    double SHOOT_SPEED = .95;
    double INTAKE_SPEED = -.95;
}
