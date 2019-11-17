package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class BOT2TeleOpMode extends LinearOpMode {
 //   private Gyroscope imu;
 //   private DigitalChannel digitalTouch;
 //   private DistanceSensor sendsorColorRange;
 //   private Servo servoTest;
 //   private DistanceSensor sensorColorRange;

    public BOT2TeleOpMode() {
    }

    @Override
    public void runOpMode() {
        // imu = hardwareMap.get(Gyroscope.class, "imu");
        DcMotor motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        DcMotor motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        //sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        //servoTest = hardwareMap.get(Servo.class, "servoTest");
        telemetry.addData("Status", "Initialized");
        //telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();
        // run until the end of the match (driver presses STOP)
        double leftPower;
        double rightPower;
        while (opModeIsActive()) {
            leftPower = -this.gamepad1.left_stick_y;
            rightPower = -this.gamepad1.right_stick_y;
            motorLeft.setPower(leftPower);
            motorRight.setPower(rightPower);
            telemetry.addData("Taget", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Motor", "left (%.2f), right (%.2f)", motorLeft.getPower(), motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }


}
