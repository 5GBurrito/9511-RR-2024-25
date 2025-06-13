package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
@Disabled

public class autoTemplate extends OpMode {

    //adds a working sleep function with the format on rest(time);
    public void sleepForMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //mecanum motors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;


    //launcher servo
    private Servo launcherServo;

    //linear slide motor
    private DcMotor slideMotor;

    //wrist servo
    private Servo wristServo;

    //hand servo
    private Servo handServo;

    private int slideZero;
    private int slidePosition;

    //stop moving function
    private void stopMoving() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //move forward function
    public void moveForward(int length) {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        backLeftMotor.setPower(-1);
        backRightMotor.setPower(1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //move backwards function
    public void moveBackwards(int length) {
        frontLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        backLeftMotor.setPower(1);
        backRightMotor.setPower(-1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //turn left function
    public void turnLeft(int length) {
        frontLeftMotor.setPower(1);
        frontRightMotor.setPower(1);
        backLeftMotor.setPower(1);
        backRightMotor.setPower(1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //turn right function
    public void turnRight(int length) {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(-1);
        backLeftMotor.setPower(-1);
        backRightMotor.setPower(-1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //strafe left function
    public void strafeLeft(int length) {
        frontLeftMotor.setPower(1);
        frontRightMotor.setPower(1);
        backLeftMotor.setPower(-1);
        backRightMotor.setPower(-1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //strafe right function
    public void strafeRight(int length) {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(-1);
        backLeftMotor.setPower(1);
        backRightMotor.setPower(1);
        sleepForMilliseconds(length);
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    private void linearSlideUp() {
        slidePosition = slideZero + 250;
        slideMotor.setTargetPosition(slidePosition);
        slideMotor.setPower(max(0.2, abs(slidePosition - slideMotor.getCurrentPosition()) / 500.0));
    }

    private void returnToTheDeepestPitOfHell() {
        slidePosition = slideZero;
        slideMotor.setTargetPosition(slidePosition);
        slideMotor.setPower(max(0.2, abs(slidePosition - slideMotor.getCurrentPosition()) / 500.0));
    }

    public void DROPDROPDROPDROPDROP() {
        handServo.setPosition(0);
    }

    public void KYSNOW() {
        returnToTheDeepestPitOfHell();
        requestOpModeStop();
    }

    @Override
    public void init() {

        //mecanum motors
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        backRightMotor = hardwareMap.dcMotor.get("backRight");

        //launcher servo
        launcherServo = hardwareMap.servo.get("launcherServo");
        //launcherServo.setPosition(0);
        launcherServo.setPosition(0.75);

        //linear slide servo
        slideMotor = hardwareMap.dcMotor.get("slideMotor");
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideZero = slideMotor.getCurrentPosition();
        slideMotor.setTargetPosition(slideMotor.getCurrentPosition());
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //wrist servo
        wristServo = hardwareMap.servo.get("wristServo");
        wristServo.setPosition(0);

        //hand servo
        handServo = hardwareMap.servo.get("handServo");
        handServo.setPosition(1);

    }

    @Override
    public void loop() {
        moveForward(3000);
        moveBackwards(3000);
        strafeLeft(3000);
        strafeRight(3000);
        turnLeft(3000);
        turnRight(3000);
        KYSNOW();
    }
}