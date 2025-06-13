package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous

//parking to right

public class leftAuto extends OpMode {

    //adds a working sleep function with the format on rest(time);
    public void sleepForMilliseconds(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    private DcMotor slideMotor;

    private DcMotor leftLift;
    private DcMotor rightLift;
    private boolean running = true;

    private int leftBottom;
    private int leftPosition;

    private int rightBottom;
    private int rightPosition;

    private int slideBottom;
    private int slidePosition;

    private Servo wristServo;
    private Servo handServo;
    private Boolean reachInside;
    private Boolean lastCrossState;



    //stop moving function
    private void stopMoving() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    //move forward function
    public void moveForward(int length) {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    //move backwards function
    public void moveBackwards(int length) {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(-0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    //turn left function
    public void turnLeft(int length) {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    //turn right function
    public void turnRight(int length) {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    //strafe left function
    public void strafeLeft(int length) {
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    //strafe right function
    public void strafeRight(int length) {
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleepForMilliseconds(length);
        stopMoving();
    }

    private void moveSlideTo(int height) { //top bin is 4800
        slidePosition = slideBottom + height;
        slideMotor.setTargetPosition(slidePosition);
        slideMotor.setPower(max(0.2, abs(slidePosition - slideMotor.getCurrentPosition()) / 500.0));
    }

    private void returnToTheDeepestPitOfHell() {
        slidePosition = slideBottom;
        slideMotor.setTargetPosition(slidePosition);
        slideMotor.setPower(max(0.2, abs(slidePosition - slideMotor.getCurrentPosition()) / 500.0));
    }


        public void KYSNOW() {
            returnToTheDeepestPitOfHell();
            requestOpModeStop();
        }

    public void closeHand() {
        handServo.setPosition(0.9-0.235);
    }

    public void openHand() {
        handServo.setPosition(0.90);
    }

    public void handUp() {
        wristServo.setPosition(0);
    }
    public void handDown() {
        wristServo.setPosition(0.35);
    }


    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");

        slideMotor = hardwareMap.dcMotor.get("slideMotor");

        leftLift = hardwareMap.dcMotor.get("leftLift");
        rightLift = hardwareMap.dcMotor.get("rightLift");

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBottom = leftLift.getCurrentPosition();
        leftLift.setTargetPosition(leftLift.getCurrentPosition());
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBottom = rightLift.getCurrentPosition();
        rightLift.setTargetPosition(rightLift.getCurrentPosition());
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideBottom = slideMotor.getCurrentPosition();
        slideMotor.setTargetPosition(slideMotor.getCurrentPosition());
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        handServo = hardwareMap.servo.get("handServo");
        wristServo = hardwareMap.servo.get("wristServo");

    }

    @Override
    public void loop() {
        while (running) {
            closeHand();
            handUp();
            moveForward(100);
            strafeLeft(1100);
            turnLeft(1250);
            moveSlideTo(4800);
            sleepForMilliseconds(3500);
            moveForward(50);
            strafeRight(50);
            handDown();
            sleepForMilliseconds(500);
            openHand();
            handUp();
            moveSlideTo(0);
            turnRight(1250);
            moveForward(500);
            strafeRight(6000);
            moveBackwards(700);
            //KYSNOW();
            running = false;
        }
    }
}
