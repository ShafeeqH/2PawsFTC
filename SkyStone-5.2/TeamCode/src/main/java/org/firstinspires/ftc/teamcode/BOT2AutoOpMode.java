package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class BOT2AutoOpMode extends LinearOpMode {
//    private Gyroscope imu;
//    private DigitalChannel digitalTouch;
//    private DistanceSensor sensorColorRange;
//    private Servo servoTest;
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        DcMotor motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        DcMotor motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        //sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        //servoTest = hardwareMap.get(Servo.class, "servoTest");
        telemetry.addData("status", "initialized");
        //telemetry.update();
        //Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();
        //run until the end of the match (driver presses STOP)
        double leftPower;
        double rightPower;
        runtime.reset();
        leftPower=0.68;
        rightPower=0.68;
        motorLeft.setPower(leftPower);
        motorRight.setPower(rightPower);
        while (opModeIsActive() && (runtime.seconds() < 4.0)) {
            telemetry.addData("Taget", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Motor", "left (%.2f), right (%.2f)", motorLeft.getPower(), motorRight.getPower());
            telemetry.addData("Status","Running");
            telemetry.update();

        }
    }
}
