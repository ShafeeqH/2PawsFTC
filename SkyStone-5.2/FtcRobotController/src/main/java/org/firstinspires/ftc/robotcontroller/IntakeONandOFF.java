package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;

@TeleOp

public class IntakeONandOFF extends LinearOpMode {
   // private Gyroscope imu;
    private DcMotor intakeMotor1;
    private DcMotor intakeMotor2;
   // private DigitalChannel digitalTouch;
   // private DustanceSensor sensoColorRange;
   // private Servo servoTest;


    @Override
    public void runOpMode(){
       // imu = hardwareMap.get(Gyroscope.class, "imu");
        intakeMotor1 = hardwareMap.get(DcMotor.class, "leftWheel");
        intakeMotor2 = hardwareMap.get (DcMotor.class, "rightWheel");
        intakeMotor1.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        telemetry.addData("Status", "Running");
        telemetry.update();

        int tgtMotor = 0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if((gamepad1.a) && (tgtMotor==0)) {
                intakeMotor1.setPower(1);
                intakeMotor2.setPower(1);
                tgtMotor = 1;
            }else if ((gamepad1.a) && (tgtMotor==1)){
                intakeMotor1.setPower(0);
                intakeMotor2.setPower(0);
                tgtMotor = 0;
            }
            telemetry.update();
        }

    }

}
