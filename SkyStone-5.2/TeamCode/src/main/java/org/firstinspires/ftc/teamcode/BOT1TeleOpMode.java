package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp

public class BOT1TeleOpMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor leftFrontMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor leftRearMotor = null;
    DcMotor rightRearMotor = null;
    DcMotor armMotor = null;
    DcMotor leftIntakeMotor = null;
    DcMotor rightIntakeMotor = null;
    Servo gripServo;
    Servo wristServo;
    Servo pushServo;

    // Declare Variables and initial Values
    double joyScale = 0.5;
    double motorMax = 0.6;
    double X1, Y1, X2, Y2;
    double rfPower, lfPower, rrPower, lrPower;
    double  ARM_POWER = .25;
    int     MIN_ARM_POSITION = 10,MAX_ARM_POSITION = 600,INITIAL_ARM_POSITION = 25, ARM_POSITION_STEP = 5;
    double  gripPosition, wristPosition, pushPosition;
    double  GRIP_MIN_POSITION = 0, GRIP_MAX_POSITION = 1, WRIST_MIN_POSITION = 0, WRIST_MAX_POSITION = 1, PUSH_MIN_POSITION =0, PUSH_MAX_POSITION =1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        int armPosition = INITIAL_ARM_POSITION;         //  Set the Arm Position to the Initial Position

        //Load Hardware Map
        leftFrontMotor = hardwareMap.get(DcMotor.class,"LF");
        rightFrontMotor = hardwareMap.get(DcMotor.class,"RF");
        leftRearMotor = hardwareMap.get(DcMotor.class,"LR");
        rightRearMotor = hardwareMap.get(DcMotor.class,"RR");
        armMotor = hardwareMap.get(DcMotor.class,"ARM");
        leftIntakeMotor = hardwareMap.get(DcMotor.class, "LW");
        rightIntakeMotor = hardwareMap.get (DcMotor.class, "RW");
        gripServo = hardwareMap.get(Servo.class, "PINCH");
        wristServo = hardwareMap.get(Servo.class, "WRIST");
        pushServo = hardwareMap.get(Servo.class, "PUSH");

        // Set the drive motor direction:
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection((DcMotor.Direction.FORWARD));
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set the drive motor run modes:
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the Power for the arm Motor
        armMotor.setPower(ARM_POWER);
        gripPosition = GRIP_MAX_POSITION;
        wristPosition = WRIST_MIN_POSITION;
        pushPosition = PUSH_MIN_POSITION;

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            // Reset speed variables
            lfPower = 0; rfPower = 0; lrPower = 0; rrPower = 0;
            rightIntakeMotor.setPower(0);
            leftIntakeMotor.setPower(0);

            Y1 = -gamepad1.left_stick_y * joyScale; // invert so up is positive
            X1 = gamepad1.left_stick_x * joyScale;
           // Y2 = -gamepad1.right_stick_y * joyScale;
            X2 = gamepad1.right_stick_x * joyScale;
           // X2 = gamepad1.right_trigger * joyScale;

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

            // Assign Power to the Motors
            leftFrontMotor.setPower(lfPower);
            rightFrontMotor.setPower(rfPower);
            leftRearMotor.setPower(lrPower);
            rightRearMotor.setPower(rrPower);

            // Assign Position Value to the Arm Motor
            if (gamepad1.dpad_up && armMotor.getCurrentPosition() < MAX_ARM_POSITION) armPosition = armPosition + ARM_POSITION_STEP;
            if (gamepad1.dpad_down && armMotor.getCurrentPosition() > MIN_ARM_POSITION) armPosition = armPosition - ARM_POSITION_STEP;
            armMotor.setTargetPosition(armPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Check to see if Intake Wheels needs to spin
            if (gamepad1.x){
                rightIntakeMotor.setPower(1);
                leftIntakeMotor.setPower(1);
            }
            // Assign Position Value to the Grip Servo
            if (gamepad1.y && gripPosition < GRIP_MAX_POSITION) gripPosition = gripPosition + .01;
            if (gamepad1.a && gripPosition > GRIP_MIN_POSITION) gripPosition = gripPosition - .01;
            gripServo.setPosition(Range.clip(gripPosition, GRIP_MIN_POSITION, GRIP_MAX_POSITION));

            // Assign Position Value to the Wrist Servo
            if (gamepad1.dpad_left && wristPosition < WRIST_MAX_POSITION) wristPosition = wristPosition + .01;
            if (gamepad1.dpad_right && wristPosition > WRIST_MIN_POSITION) wristPosition = wristPosition - .01;
            wristServo.setPosition(Range.clip(wristPosition, WRIST_MIN_POSITION, WRIST_MAX_POSITION));

            // Assign Position Value to the Push Servo
            if (gamepad1.right_bumper && pushPosition < PUSH_MAX_POSITION) pushPosition = pushPosition + .01;
            if (gamepad1.left_bumper && pushPosition > PUSH_MIN_POSITION) pushPosition = pushPosition - .01;
            pushServo.setPosition(Range.clip(pushPosition, PUSH_MIN_POSITION, PUSH_MAX_POSITION));

            telemetry.addData("Stick", "Y1 (%.2f), X2 (%.2f)",Y1,X2);
            telemetry.addData("Front", "left (%.2f), right (%.2f)", lfPower, rfPower);
            telemetry.addData("Rear", "left (%.2f), right (%.2f)", lrPower, rrPower);

        }

    }
}