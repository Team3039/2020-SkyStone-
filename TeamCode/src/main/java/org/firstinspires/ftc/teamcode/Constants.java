package org.firstinspires.ftc.teamcode;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public interface Constants {

    double DRIVE_PPR = 280; // Neverest 40
    double WHEEL_DIAMETER = 4; // 4" Mecanum
    double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI; // pi * diameter
    double PPR_TO_INCHES = WHEEL_CIRCUMFERENCE / DRIVE_PPR; // (circumference / pulses in 1 Revolution) = (x / 1 pulse)

}
