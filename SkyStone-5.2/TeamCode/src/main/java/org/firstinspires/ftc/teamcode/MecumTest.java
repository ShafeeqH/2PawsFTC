package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp

public class MecumTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftFrontMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor leftRearMotor = null;
    DcMotor rightRearMotor = null;

    double joyScale = 0.5;
    double motorMax = 0.6;
    double X1;
    double Y1;
    double X2;
    double Y2;
    double rfPower;
    double lfPower;
    double rrPower;
    double lrPower;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
        leftRearMotor = hardwareMap.dcMotor.get("leftRear");
        rightRearMotor = hardwareMap.dcMotor.get("rightRear");

        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
            Y1 = -gamepad1.right_stick_y * joyScale; // invert so up is positive
            X1 = gamepad1.right_stick_x * joyScale;
            Y2 = -gamepad1.left_stick_y * joyScale;
            X2 = gamepad1.left_stick_x * joyScale;

            // Forward/back movement
            lfPower += Y1;
            rfPower += Y1;
            lrPower += Y1;
            rrPower += Y1;

            // Side to side movement
            lfPower += X1;
            rfPower -= X1;
            lrPower -= X1;
            rrPower += X1;

            // Rotation movement
            lfPower += X2;
            rrPower -= X2;
            lrPower += X2;
            rrPower -= X2;

            // Clip motor power values to +-motorMax
            lfPower = Math.max(-motorMax, Math.min(lfPower, motorMax));
            rfPower = Math.max(-motorMax, Math.min(rfPower, motorMax));
            lrPower = Math.max(-motorMax, Math.min(lrPower, motorMax));
            rrPower = Math.max(-motorMax, Math.min(rrPower, motorMax));
            leftFrontMotor.setPower(lfPower);
            rightFrontMotor.setPower(rfPower);
            leftRearMotor.setPower(lrPower);
            rightRearMotor.setPower(rrPower);

            telemetry.addData("Front", "left (%.2f), right (%.2f)", lfPower, rfPower);
            telemetry.addData("Rear", "left (%.2f), right (%.2f)", lrPower, rrPower);

        }

    }
}