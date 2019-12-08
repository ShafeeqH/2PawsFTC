package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
//@Disabled
public class BOT1Auto_Left_OUT extends LinearOpMode {
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
    double motorMax = 0.9;
    double X1, Y1, X2, Y2;
    double rfPower, lfPower, rrPower, lrPower;
    double ARM_POWER = .40;
    int FORWARD =1,LEFT=2,RIGHT=3,REVERSE=4;
    int MIN_ARM_POSITION = -800, MAX_ARM_POSITION = 10, INITIAL_ARM_POSITION = 10, ARM_POSITION_STEP = 5, SAFE_ARM_POSITION = -450;
    double gripPosition, wristPosition, pushPosition;
    double GRIP_MIN_POSITION = 0.40, GRIP_MAX_POSITION = 1, WRIST_MIN_POSITION = 0, WRIST_MAX_POSITION = 1, PUSH_MIN_POSITION = 0, PUSH_MAX_POSITION = 0.4;

    @Override

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        int armPosition = INITIAL_ARM_POSITION;         //  Set the Arm Position to the Initial Position

        //Load Hardware Map
        leftFrontMotor = hardwareMap.get(DcMotor.class, "LF");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "RF");
        leftRearMotor = hardwareMap.get(DcMotor.class, "LR");
        rightRearMotor = hardwareMap.get(DcMotor.class, "RR");
        armMotor = hardwareMap.get(DcMotor.class, "ARM");
        leftIntakeMotor = hardwareMap.get(DcMotor.class, "LW");
        rightIntakeMotor = hardwareMap.get(DcMotor.class, "RW");
        gripServo = hardwareMap.get(Servo.class, "PINCH");
        wristServo = hardwareMap.get(Servo.class, "WRIST");
        pushServo = hardwareMap.get(Servo.class, "PUSH");

        // Set the drive motor direction:
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection((DcMotor.Direction.REVERSE));
        leftIntakeMotor.setDirection(DcMotor.Direction.REVERSE);
        rightIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set the drive motor run modes:
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRearMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Set the Power for the arm Motor
        // int armPosition = INITIAL_ARM_POSITION;         //  Set the Arm Position to the Initial Position

        armMotor.setPower(ARM_POWER);
        //   INITIAL_ARM_POSITION = armMotor.getCurrentPosition();
        //   armMotor.setTargetPosition(INITIAL_ARM_POSITION);
        //   armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armPosition = INITIAL_ARM_POSITION;
        armMotor.setTargetPosition(INITIAL_ARM_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        gripPosition = GRIP_MIN_POSITION;
        wristPosition = WRIST_MAX_POSITION;
        pushPosition = PUSH_MIN_POSITION;

        waitForStart();
        runtime.reset();

       // while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            // Reset speed variables
            lfPower = 0;
            rfPower = 0;
            lrPower = 0;
            rrPower = 0;
            rightIntakeMotor.setPower(0);
            leftIntakeMotor.setPower(0);

            autoDrive(0.9,1,1.5);
            autoDrive(0.9,2,1.0);
            autoDrive(0.9,1,1.1);
            //autoDrive(0.9,4,5);
/**
            // Assign Position Value to the Arm Motor
            if (gamepad2.dpad_down && armMotor.getCurrentPosition() < MAX_ARM_POSITION)
                armPosition = armPosition + ARM_POSITION_STEP;
            if (gamepad2.dpad_up && armMotor.getCurrentPosition() > MIN_ARM_POSITION)
                armPosition = armPosition - ARM_POSITION_STEP;
            armMotor.setTargetPosition(armPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Check to see if Intake Wheels needs to spin
            if (gamepad2.x) {
                rightIntakeMotor.setPower(1);
                leftIntakeMotor.setPower(1);
            }
            // Assign Position Value to the Grip Servo
            if (gamepad2.y && gripPosition < GRIP_MAX_POSITION) gripPosition = gripPosition + .01;
            if (gamepad2.a && gripPosition > GRIP_MIN_POSITION) gripPosition = gripPosition - .01;
            gripServo.setPosition(Range.clip(gripPosition, GRIP_MIN_POSITION, GRIP_MAX_POSITION));

            // Assign Position Value to the Wrist Servo
            if (gamepad2.dpad_right && wristPosition < WRIST_MAX_POSITION)
                wristPosition = wristPosition + .01;
            if (gamepad2.dpad_left && wristPosition > WRIST_MIN_POSITION)
                wristPosition = wristPosition - .01;
            wristServo.setPosition(Range.clip(wristPosition, WRIST_MIN_POSITION, WRIST_MAX_POSITION));

            // Assign Position Value to the Push Servo
            if (gamepad2.right_bumper && pushPosition < PUSH_MAX_POSITION)
                pushPosition = pushPosition + .01;
            if (gamepad2.left_bumper && pushPosition > PUSH_MIN_POSITION)
                pushPosition = pushPosition - .01;
            pushServo.setPosition(Range.clip(pushPosition, PUSH_MIN_POSITION, PUSH_MAX_POSITION));

            //Reset ARM
            if (gamepad2.b) {
                armPosition = SAFE_ARM_POSITION;
                armMotor.setTargetPosition(armPosition);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // Wait until the ARM is clear
                while (armMotor.isBusy()) {

                    sleep(500);
                }
                // Reset all Servos to Home
                gripPosition = GRIP_MIN_POSITION;
                wristPosition = WRIST_MAX_POSITION;
                pushPosition = PUSH_MIN_POSITION;
                gripServo.setPosition(Range.clip(gripPosition, GRIP_MIN_POSITION, GRIP_MAX_POSITION));
                wristServo.setPosition(Range.clip(wristPosition, WRIST_MIN_POSITION, WRIST_MAX_POSITION));
                pushServo.setPosition(Range.clip(pushPosition, PUSH_MIN_POSITION, PUSH_MAX_POSITION));

                sleep(1000);

                armPosition = INITIAL_ARM_POSITION;
                //   armMotor.setTargetPosition(INITIAL_ARM_POSITION);
                //   armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } **/

            telemetry.addData("POS", "ENC (%04d), ARM (%04d)", armMotor.getCurrentPosition(), armPosition);
            telemetry.addData("Stick", "grip (%.2f), push (%.2f) wrist (%.2f)", gripServo.getPosition(), pushServo.getPosition(), wristServo.getPosition());
            //  telemetry.addData("Front", "left (%.2f), right (%.2f)", lfPower, rfPower);
            //  telemetry.addData("Rear", "left (%.2f), right (%.2f)", lrPower, rrPower);
            // telemetry.update();
      //  }

    }
        public void autoDrive ( double speed,
        int direction,
        double timeoutS){

        if (direction == 1) {
            // Forward/back movement
            lfPower = -speed;
            rfPower = -speed;
            lrPower = -speed;
            rrPower = -speed;
        } else if (direction == 2) {
            // Side to side movement Left
            lfPower = speed;
            rfPower = -speed;
            lrPower = speed;
            rrPower = -speed;
            } else if (direction == 3){
            // Side to side movement Right
            lfPower = -speed;
            rfPower = speed;
            lrPower = -speed;
            rrPower = speed;
        } else if (direction == 4){
            // Back movement
            lfPower = speed;
            rfPower = speed;
            lrPower = speed;
            rrPower = speed;
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < timeoutS)){
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
        }
            lfPower = 0;
            rfPower = 0;
            lrPower = 0;
            rrPower = 0;

            // Assign Power to the Motors
            leftFrontMotor.setPower(lfPower);
            rightFrontMotor.setPower(rfPower);
            leftRearMotor.setPower(lrPower);
            rightRearMotor.setPower(rrPower);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
        }
    }

