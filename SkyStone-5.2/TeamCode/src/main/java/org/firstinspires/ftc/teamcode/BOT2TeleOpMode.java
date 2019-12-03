package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class BOT2TeleOpMode extends LinearOpMode {
    //private Gyroscope imu;
 //   private DigitalChannel digitalTouch;
 //   private DistanceSensor sensorColorRange;
 //   private DistanceSensor sensorColorRange;

 //   double  gripPosition,contPower;
 //   double  MIN_GRIP_POSITION = 0, MAX_GRIP_POSITION = 1, GRIP_POSITION_STEP = 0.01;
    double  ARM_POWER = .25;
    int     MIN_ARM_POSITION = 10,MAX_ARM_POSITION = 600,INITIAL_ARM_POSITION = 25, ARM_POSITION_STEP = 5;

    @Override
    public void runOpMode() {
        // Initiliaze Values
        double leftPower;  // Left Wheel Power
        double rightPower; // Right Wheel Power
        double gripPower;  // Grip Wheel Power
        int armPosition = INITIAL_ARM_POSITION;         //  Set the Arm Position to the Initial Position


        // imu = hardwareMap.get(Gyroscope.class, "imu");
        DcMotor motorLeft = hardwareMap.get(DcMotor.class, "leftWheel");
        DcMotor motorRight = hardwareMap.get(DcMotor.class, "rightWheel");
        DcMotor armMotor = hardwareMap.get(DcMotor.class,"armMotor");
        CRServo gripServo = hardwareMap.get(CRServo.class, "pinchServo");
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        //sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");

        // Set the directions for all Motors
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection((DcMotor.Direction.FORWARD));

        // Set arm Motor to use Encoder and reset the Encoder
       // armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the Power for the arm Motor
        armMotor.setPower(ARM_POWER);
        armPosition = INITIAL_ARM_POSITION;
        armMotor.setTargetPosition(INITIAL_ARM_POSITION);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()){
            // get left and right wheel power values from the y sticks
            leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;

            // Assign the Values to run the Wheel Motors
            motorLeft.setPower(leftPower);
            motorRight.setPower(rightPower);

            // Get the Values for the Grip Power from the DPAD Left and Right
            if (gamepad1.dpad_left)
                gripPower = .20;
            else if (gamepad1.dpad_right)
                gripPower = -.20;
            else
                gripPower = 0.0;

            gripServo.setPower(gripPower);


            if (gamepad1.dpad_up && armMotor.getCurrentPosition() < MAX_ARM_POSITION) armPosition = armPosition + ARM_POSITION_STEP;
            if (gamepad1.dpad_down && armMotor.getCurrentPosition() > MIN_ARM_POSITION) armPosition = armPosition - ARM_POSITION_STEP;
            armMotor.setTargetPosition(armPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("Encoder Position", armMotor.getCurrentPosition());
            telemetry.addData("Wheel Taget", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Motor", "left (%.2f), right (%.2f)", motorLeft.getPower(), motorRight.getPower());
            telemetry.addData("Arm Target", "TGT (%04d), POS (%4d)", armPosition, armMotor.getCurrentPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();
            idle();

        }
    }
}
