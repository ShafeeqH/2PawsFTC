package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
@Disabled
public class ServoTest extends LinearOpMode {

    Servo gripServo;
    DcMotor armMotor;

    double  gripPosition;
    double  MIN_POSITION = 0, MAX_POSITION = 1;
    @Override
    public void runOpMode()
    {
        gripServo = hardwareMap.servo.get("pinchServo");
    //    armMotor = hardwareMap.dcMotor.get("armMotor");

    //    int armPosition = armMotor.getCurrentPosition()
        telemetry.addData("Mode", "waiting");
        telemetry.update();

        gripPosition = MAX_POSITION;
        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("Mode", "running");
            if (gamepad1.y && gripPosition < MAX_POSITION) gripPosition = gripPosition + .01;
            if (gamepad1.a && gripPosition > MIN_POSITION) gripPosition = gripPosition - .01;
            gripServo.setPosition(Range.clip(gripPosition, MIN_POSITION, MAX_POSITION));
            telemetry.addData("pinch servo", "position=" + gripPosition + "    actual=" + gripServo.getPosition());
            telemetry.update();
            idle();
        }
    }
}
