package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Slide Test", group = "Concept")
public class AutoSlideTest extends LinearOpMode {

    private DcMotor leftSlide = null;
    //Contains Left Encoder
    private DcMotor rightSlide = null;
    //Contains Right Encoder
    private DcMotor intake = null;
    //Contains Center Encoder

    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;

    Servo leftClaw;
    Servo rightClaw;

    Servo leftTilt;
    Servo rightTilt;

    double leftPos;
    double rightPos;

    private ElapsedTime runtime = new ElapsedTime();


    public void runOpMode() {

        leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");

        intake = hardwareMap.get(DcMotor.class, "intake");

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");

        leftTilt = hardwareMap.get(Servo.class, "leftTilt");
        rightTilt = hardwareMap.get(Servo.class, "rightTilt");

        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData(">", "Press Start to scan Tele Ops.");
        telemetry.update();

        leftTilt.setPosition(0.39);
        rightTilt.setPosition(0.54);

        leftClaw.setPosition(0.82);
        rightClaw.setPosition(0.63);

        waitForStart();

        if (opModeIsActive()) {
            //move(26, 0.3);

            intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intake.setPower(-0.2);
            sleep(3000);
            intake.setPower(0.0);
            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
/*
            move(-10, 0.3);
            sleep(500);
            strafe(-5, 0.4);
            sleep(500);
            turn(90, 0.4);
            move(-14, 0.5);
            sleep(500);
            strafe(-13, 0.5);
            sleep(500);
            move(-11, 0.5);
*/
            leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            leftSlide.setPower(0.35);
            rightSlide.setPower(-0.35);
            sleep(1000);
            leftSlide.setPower(0.0);
            rightSlide.setPower(0.0);

            leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            runtime.reset();
  /*
            while(runtime.milliseconds() < 1500){
                leftFront.setPower(-0.2);
                leftBack.setPower(-0.2);
                rightFront.setPower(-0.2);
                rightBack.setPower(-0.2);
            }
*/
            brake();

            runtime.reset();
            while(runtime.milliseconds() < 2000){
                leftPos = leftTilt.getPosition();
                rightPos = rightTilt.getPosition();

                leftPos += 0.005;
                rightPos -= 0.005;

                leftTilt.setPosition(leftPos);
                rightTilt.setPosition(rightPos);
            }

            leftClaw.setPosition(0.92);
            rightClaw.setPosition(0.5);

            sleep(500);

            leftTilt.setPosition(0.39);
            rightTilt.setPosition(0.54);

            leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftSlide.setPower(-0.15);
            rightSlide.setPower(0.15);
            sleep(2000);

            leftSlide.setPower(0.0);
            rightSlide.setPower(0.0);
            leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //strafe(20, 0.5);
            brake();




        }
    }

    public void brake(){
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        //leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //0: Left, 1: Right, 2: Center
    public double[] getRot(){
        double[] rotations = new double[3];
        rotations[1] = 0.0;
        //rotations[0] = (-1 * leftSlide.getCurrentPosition()) / 2048.0;
        rotations[1] = (rightSlide.getCurrentPosition()) / 2048.0;
        rotations[2] = (intake.getCurrentPosition()) / 2048.0;

        return rotations;
    }

    public double[] getDist(){
        double[] distance = new double[3];
        double[] rotations = getRot();

        //Distace = Rotations * 48 millimeters * Pi / 25.4 (mi --> in)
        distance[0] = rotations[0] * 48 * Math.PI / 25.4;
        distance[1] = rotations[1] * 48 * Math.PI / 25.4;
        distance[2] = rotations[2] * 48 * Math.PI / 25.4;

        return distance;
    }

    public void displayEncoderData(){
        telemetry.addData("Encoder Position(L, R, C)",  "%7d :%7d",
                rightSlide.getCurrentPosition(),
                intake.getCurrentPosition());

        telemetry.addData("Encoder Rotations(L, R, C)",  "%7d :%7d",
                (rightSlide.getCurrentPosition() / 2048),
                (intake.getCurrentPosition() / 2048));

        double[] distance = getDist();
        telemetry.addData("Distance in Inches(L, R, C)",  "%7f :%7f :%7f",
                distance[0],
                distance[1],
                distance[2]);
    }

    public void move(int dist, double speed){
        while (abs(getDist()[1]) < abs(dist)){
            displayEncoderData();

            if (dist < 0){
                leftFront.setPower(-speed);
                leftBack.setPower(-speed);
                rightFront.setPower(-speed);
                rightBack.setPower(-speed);
                telemetry.addData("Direction", "Backward");

            }

            else{
                leftFront.setPower(speed);
                leftBack.setPower(speed);
                rightFront.setPower(speed);
                rightBack.setPower(speed);
                telemetry.addData("Direction", "Forward");
            }

            telemetry.update();

        }
        brake();

    }

    public void turn(int angle, double speed){

        while (abs(rightSlide.getCurrentPosition()) < (40 * abs(angle))) {
            displayEncoderData();
            if (angle < 0) {
                leftFront.setPower(-speed);
                leftBack.setPower(-speed);
                rightFront.setPower(speed);
                rightBack.setPower(speed);
                telemetry.addData("Direction", "Left");
            } else {
                leftFront.setPower(speed);
                leftBack.setPower(speed);
                rightFront.setPower(-speed);
                rightBack.setPower(-speed);
                telemetry.addData("Direction", "Right");
            }
            telemetry.update();

        }
        brake();
    }

    public void strafe(int dist, double speed){
        while (abs(getDist()[2]) < abs(dist)){
            displayEncoderData();

            if (dist < 0){
                leftFront.setPower(-speed);
                leftBack.setPower(speed);
                rightFront.setPower(speed);
                rightBack.setPower(-speed);
                telemetry.addData("Direction", "Left");

            }

            else{
                leftFront.setPower(speed);
                leftBack.setPower(-speed);
                rightFront.setPower(-speed);
                rightBack.setPower(speed);
                telemetry.addData("Direction", "Right");
            }

            telemetry.update();

        }
        brake();

    }
}