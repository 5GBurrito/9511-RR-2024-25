//motor 1, top: -75, bottom: 82
//motor 2, top: 144, bottom: 105

package org.firstinspires.ftc.teamcode.old;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class liftTest extends LinearOpMode {

    private DcMotor leftLift;
    private DcMotor rightLift;
    private DcMotor slideMotor;

    @Override
    public void runOpMode() {
        leftLift = hardwareMap.get(DcMotor.class, "leftLift"); // Replace "motor1" with the actual name of your motor
        rightLift = hardwareMap.get(DcMotor.class, "rightLift"); // Replace "motor2" with the actual name of your motor
        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        // Set motor run modes to encoders



        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {
            // Read encoder values
            int encoder1Value = leftLift.getCurrentPosition();
            int encoder2Value = rightLift.getCurrentPosition();
            int slideval = slideMotor.getCurrentPosition();

            // Send encoder values to telemetry (display on the driver station)
            telemetry.addData("Left Encoder Value", encoder1Value);
            telemetry.addData("Right Encoder Value", encoder2Value);
            telemetry.addData("slide", slideval);

            // Control motors using gamepad 1's left and right sticks
            double leftStickPower = -gamepad1.left_stick_y;
            double rightStickPower = -gamepad1.right_stick_y;
            leftLift.setPower(leftStickPower);
            rightLift.setPower(rightStickPower);

            telemetry.update();
        }
    }
}