package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class MyFirstJavaOpMode extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DigitalChannel digitalTouch;
    private DistanceSensor sendsorColorRange;
    private Servo servoTest;
    private DistanceSensor sensorColorRange;

    @Override
    public void runOpMode() {
       // imu = hardwareMap.get(Gyroscope.class, "imu");
        motorLeft = hardwareMap.get(DcMotor.class, "leftWheel");
        motorRight = hardwareMap.get(DcMotor.class,"rightWheel");
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
        double tgtleftMotor;
        double tgtrightMotor;
        while (opModeIsActive()) {
            tgtleftMotor = -this.gamepad1.left_stick_y;
             tgtrightMotor  = -this.gamepad1.right_stick_y;
            motorLeft.setPower(tgtleftMotor);
            motorRight.setPower(tgtrightMotor);
            telemetry.addData("Target power", "target left");
            telemetry.addData("Target power"," target right");
            telemetry.addData("Motor Power", motorLeft.getPower());
            telemetry.addData("Motor Power", motorRight.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}
