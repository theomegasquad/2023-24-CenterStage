package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Tele Ops", group = "Concept")
public class TeleOps extends LinearOpMode {

    private DcMotor leftSlide   = null;
    private DcMotor rightSlide  = null;

    private DcMotor intake = null;

    private DcMotor leftFront   = null;
    private DcMotor rightFront  = null;
    private DcMotor leftBack   = null;
    private DcMotor rightBack  = null;

    Servo leftClaw;
    Servo rightClaw;

    Servo leftTilt;
    Servo rightTilt;

    private ElapsedTime runtime = new ElapsedTime();
    boolean intakeMoving = true;


    public void runOpMode() {

        leftSlide  = hardwareMap.get(DcMotor.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");

        intake  = hardwareMap.get(DcMotor.class, "intake");

        leftFront  = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack  = hardwareMap.get(DcMotor.class, "leftBack");
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

        double turnspeed;
        double leftPos;
        double rightPos;

        telemetry.addData(">", "Press Start to scan Tele Ops." );
        telemetry.update();

        leftTilt.setPosition(0.39);
        rightTilt.setPosition(0.54);

        leftClaw.setPosition(0.97);
        rightClaw.setPosition(0.54);

        waitForStart();

        if (opModeIsActive()) {
            runtime.reset();

            while (opModeIsActive()) {

                if (gamepad2.right_bumper) {
                    leftSlide.setPower(0.85);
                    rightSlide.setPower(-0.85);
                } else if (gamepad2.left_bumper) {
                    leftTilt.setPosition(0.39);
                    rightTilt.setPosition(0.54);
                    leftSlide.setPower(-0.4);
                    rightSlide.setPower(0.4);
                } else if (gamepad2.right_trigger != 0) {
                    leftSlide.setPower(0.17);
                    rightSlide.setPower(-0.17);
                } else if (gamepad2.left_trigger != 0) {
                    leftTilt.setPosition(0.39);
                    rightTilt.setPosition(0.54);
                    leftSlide.setPower(-0.15);
                    rightSlide.setPower(0.15);

                } else {
                    leftSlide.setPower(0.0);
                    rightSlide.setPower(0.0);

                }
                if (gamepad2.b) {
                    if (intakeMoving){
                        intake.setPower(0.4);
                    } else {
                        intake.setPower(0.0);
                    }

                } else if (gamepad2.a) {
                    intake.setPower(0.48);

                } else if ((gamepad2.dpad_left) || (gamepad2.dpad_right)){
                    intake.setPower(-0.8);

                } else {
                    intake.setPower(0.0);
                }

                if (gamepad2.y) {
                    leftClaw.setPosition(1.0);
                    rightClaw.setPosition(0.54);

                } else if (gamepad2.x) {
                    leftClaw.setPosition(0.78);
                    rightClaw.setPosition(0.67);

                }

                if (gamepad2.left_stick_button) {
                    leftTilt.setPosition(0.39);
                    rightTilt.setPosition(0.54);

                } else if (gamepad2.right_stick_button) {
                    leftTilt.setPosition(0.61);
                    rightTilt.setPosition(0.34);
                }

                if (gamepad1.left_stick_y != 0) {
                    if ((gamepad1.left_stick_x < 0.1) && (gamepad1.left_stick_x > -0.1)) {
                        leftFront.setPower(gamepad1.left_stick_y);
                        leftBack.setPower(gamepad1.left_stick_y);
                        rightFront.setPower(-gamepad1.left_stick_y);
                        rightBack.setPower(-gamepad1.left_stick_y);

                    } else {
                        turnspeed = gamepad1.left_stick_x;
                        if (turnspeed > 0){
                            leftFront.setPower(gamepad1.left_stick_y);
                            leftBack.setPower(gamepad1.left_stick_y);
                            rightFront.setPower(-0.2*gamepad1.left_stick_y);
                            rightBack.setPower(-0.2*gamepad1.left_stick_y);
                        } else {
                            leftFront.setPower(0.2*gamepad1.left_stick_y);
                            leftBack.setPower(0.2*gamepad1.left_stick_y);
                            rightFront.setPower(-gamepad1.left_stick_y);
                            rightBack.setPower(-gamepad1.left_stick_y);
                        }
                    }

                } else if (gamepad1.left_stick_x != 0) {
                    turnspeed = gamepad1.left_stick_x;
                    leftFront.setPower(-turnspeed);
                    leftBack.setPower(-turnspeed);
                    rightFront.setPower(-turnspeed);
                    rightBack.setPower(-turnspeed);

                }
                else if (gamepad1.right_stick_y != 0) {
                    leftFront.setPower(gamepad1.right_stick_y * 0.5);
                    leftBack.setPower(gamepad1.right_stick_y * 0.5);
                    rightFront.setPower(-gamepad1.right_stick_y * 0.5);
                    rightBack.setPower(-gamepad1.right_stick_y * 0.5);

                } else if (gamepad1.dpad_left) {
                    leftFront.setPower(1);
                    leftBack.setPower(-1);
                    rightFront.setPower(1);
                    rightBack.setPower(-1);

                } else if (gamepad1.dpad_right) {
                    leftFront.setPower(-1);
                    leftBack.setPower(1);
                    rightFront.setPower(-1);
                    rightBack.setPower(1);

                } else {
                    leftFront.setPower(0);
                    leftBack.setPower(0);
                    rightFront.setPower(0);
                    rightBack.setPower(0);
                }

                if (gamepad2.dpad_down) {
                    leftPos = leftTilt.getPosition();
                    rightPos = rightTilt.getPosition();

                    leftPos += 0.005;
                    rightPos -= 0.005;

                    leftTilt.setPosition(leftPos);
                    rightTilt.setPosition(rightPos);

                } else if (gamepad2.dpad_up) {
                    leftPos = leftTilt.getPosition();
                    rightPos = rightTilt.getPosition();

                    leftPos -= 0.005;
                    rightPos += 0.005;

                    leftTilt.setPosition(leftPos);
                    rightTilt.setPosition(rightPos);

                }

                telemetry.addData("Input", "X = " + gamepad1.left_stick_x + ". Y = " + gamepad1.left_stick_y);
                telemetry.update();

                if (runtime.milliseconds() > 70){
                    intakeMoving = !intakeMoving;
                    runtime.reset();
            }
            }

            // Signal done;
            telemetry.addData(">", "Done");
            telemetry.update();
        }
    }
}
