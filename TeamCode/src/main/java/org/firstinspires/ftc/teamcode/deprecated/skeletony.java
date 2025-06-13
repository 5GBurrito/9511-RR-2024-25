//   ╻ ╻┏━┓┏━┓┏┓╻╻┏┓╻┏━╸     ╺┳╸┏━╸┏━┓╺┳╸   ┏━╸┏━┓╺┳┓┏━╸
//   ┃╻┃┣━┫┣┳┛┃┗┫┃┃┗┫┃╺┓      ┃ ┣╸ ┗━┓ ┃    ┃  ┃ ┃ ┃┃┣╸
//   ┗┻┛╹ ╹╹┗╸╹ ╹╹╹ ╹┗━┛ ┛    ╹ ┗━╸┗━┛ ╹    ┗━╸┗━┛╺┻┛┗━╸

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.abs;
import static java.lang.Math.max;

@TeleOp
@Disabled
@Deprecated

public class skeletony extends OpMode {

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    private DcMotor linearSlideMotor;
    private Servo elbowServo;
    private Servo wristServo;
    private Servo ligamentServo; // control how open the hand is

    private int slideZero;
    private int slidePosition;

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");

        linearSlideMotor = hardwareMap.dcMotor.get("linearSlideMotor");
        elbowServo = hardwareMap.servo.get("elbowServo");
        wristServo = hardwareMap.servo.get("wristServo");
        ligamentServo = hardwareMap.servo.get("ligamentServo");

        linearSlideMotor = hardwareMap.dcMotor.get("linearSlideMotor");
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideZero = linearSlideMotor.getCurrentPosition();
        linearSlideMotor.setTargetPosition(linearSlideMotor.getCurrentPosition());
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideZero = linearSlideMotor.getCurrentPosition();
        linearSlideMotor.setTargetPosition(linearSlideMotor.getCurrentPosition());
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void loop() {

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(-frontLeftPower);
        backLeftMotor.setPower(-backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        //linear slide control
        if (gamepad1.cross) {
            slidePosition = slideZero;
        }
        if (gamepad1.square) {
            slidePosition = slideZero + 500;
        }
        if (gamepad1.triangle) {
            slidePosition = slideZero + 1000;
        }
        if (gamepad1.left_bumper) {
            slidePosition = slideZero + 275;
        }

        linearSlideMotor.setTargetPosition(slidePosition);
        linearSlideMotor.setPower(max(0.2, abs(slidePosition - linearSlideMotor.getCurrentPosition()) / 500.0));
    }
}