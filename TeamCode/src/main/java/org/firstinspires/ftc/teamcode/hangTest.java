package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class hangTest extends OpMode {

    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    private DcMotor leftLift;
    private DcMotor rightLift;

    private int leftBottom;
    private int leftPosition;

    private int rightBottom;
    private int rightPosition;

    @Override
    public void init() {
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");

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

        if (gamepad1.share) {
            leftPosition = leftBottom;
            rightPosition = rightBottom;
        }
        if (gamepad1.options) {
            leftPosition = leftBottom + 10000;
            rightPosition = rightBottom + 10000;
        }
        if (gamepad1.ps) {
            leftPosition = leftBottom + 5000;
            rightPosition = rightBottom + 5000;
        }

        leftLift.setTargetPosition(leftPosition);
        leftLift.setPower(max(0.2, abs(leftPosition - leftLift.getCurrentPosition()) / 500.0));

        rightLift.setTargetPosition(rightPosition);
        rightLift.setPower(max(0.2, abs(rightPosition - rightLift.getCurrentPosition()) / 500.0));

    }
}
