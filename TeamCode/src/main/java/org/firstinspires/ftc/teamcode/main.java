package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class main extends OpMode {

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    private DcMotor slideMotor;

    private DcMotor leftLift;
    private DcMotor rightLift;

    private int leftBottom;
    private int leftPosition;

    private int rightBottom;
    private int rightPosition;

    private int slideBottom;
    private int slidePosition;

//    private Servo elbowServo;
    private Servo wristServo;
    private Servo handServo;
    private Boolean reachInside;
    private Boolean lastCrossState;


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

//        elbowServo = hardwareMap.servo.get("elbowServo");
        wristServo = hardwareMap.servo.get("wristServo");
        handServo = hardwareMap.servo.get("handServo");
        reachInside = false;
        lastCrossState = false;
    }

    @Override
    public void loop() {

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = max(abs(y) + abs(x) + abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(-frontLeftPower);
        backLeftMotor.setPower(-backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);


        telemetry.addData("Left Encoder Value", leftLift.getCurrentPosition());
        telemetry.addData("Right Encoder Value", rightLift.getCurrentPosition());
        telemetry.addData("Left Position Variable", leftPosition);
        telemetry.addData("Right Position Variable", rightPosition);
        telemetry.addData("Slide Position", slidePosition);

        if (gamepad1.share) {
            leftPosition = leftBottom;
            rightPosition = rightBottom;
        }
        if (gamepad1.options) {
            leftPosition = leftBottom + 13000;
            rightPosition = rightBottom + 13000;
        }
        if (gamepad1.ps) {
            leftPosition = leftBottom + 5600;
            rightPosition = rightBottom + 5600;
        }

        leftLift.setTargetPosition(leftPosition);
        leftLift.setPower(max(0.2, abs(leftPosition - leftLift.getCurrentPosition()) / 500.0));

        rightLift.setTargetPosition(rightPosition);
        rightLift.setPower(max(0.2, abs(rightPosition - rightLift.getCurrentPosition()) / 500.0));

        //arm control
        if (gamepad1.dpad_down) {
            slidePosition = slideBottom;
        }
        if (gamepad1.dpad_left) {
            slidePosition = slideBottom + 1000;
        }
        if (gamepad1.dpad_up) {
            slidePosition = slideBottom + 2500;
        }
        if (gamepad1.cross) {
            slidePosition = slidePosition - 30;
        }
        if (gamepad1.circle                               ) {
            slidePosition = slidePosition + 30;
        }

        slideMotor.setTargetPosition(slidePosition);
        slideMotor.setPower(max(0.2, abs(slidePosition - slideMotor.getCurrentPosition()) / 500.0));

        handServo.setPosition(0.90-gamepad1.right_trigger*0.250);

        //wristServo.setPosition(gamepad1.left_trigger*0.35);


        // In the loop or main control logic:
        if (gamepad1.square && !lastCrossState) {
            // Toggle the state of reachInside when the cross button is pressed
            reachInside = !reachInside;
        }

// Update the lastCrossState to the current state of the cross button
        lastCrossState = gamepad1.square;

// Control the wristServo based on the reachInside state
        if (reachInside) {
            // Set wrist position based on the left trigger input
            wristServo.setPosition(0.5 * gamepad1.left_trigger);
        } else {
            // Set wrist position to 0.5
            wristServo.setPosition(0.35 * gamepad1.left_trigger);
        }

        telemetry.addData("inside?", reachInside);
    }
}