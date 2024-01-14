package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;


@Autonomous(name = "Red Auto Right", group = "Concept")
public class RedAutoRight extends LinearOpMode {

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

    private DistanceSensor leftDist;
    private DistanceSensor rightDist;


    Servo leftClaw;
    Servo rightClaw;

    Servo leftTilt;
    Servo rightTilt;

    double leftPos;
    double rightPos;

    //Decrease if turning too much, increase if turning too little
    public int turnMult = 41;


    private ElapsedTime runtime = new ElapsedTime();

    private static final String TFOD_MODEL_ASSET = "BlueDetector.tflite";

    private static final String[] LABELS = {
            "CustomTeamElement",
    };

    private TfodProcessor tfod;

    private VisionPortal visionPortal;

    public double intakeSpeed = -0.65;


    public void runOpMode() {

        //initTfod();

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

        leftDist = hardwareMap.get(DistanceSensor.class, "leftDist");
        rightDist = hardwareMap.get(DistanceSensor.class, "rightDist");


        telemetry.addData("Status", "Initializing");
        telemetry.update();

        leftTilt.setPosition(0.40);
        rightTilt.setPosition(0.53);

        sleep(750);

        leftClaw.setPosition(0.76);
        rightClaw.setPosition(0.69);


        waitForStart();

        if (opModeIsActive()) {
            int randomization = randDetectDS();

            move(-13, 0.4);
            turn(-80, 0.4);


            runtime.reset();
            while (runtime.milliseconds() < 2500){
                double speed = -0.3;
                leftFront.setPower(speed);
                leftBack.setPower(-speed);
                rightFront.setPower(-speed);
                rightBack.setPower(speed);
            }

            strafe(3, 0.4);
            move(-23, 0.5);

            runtime.reset();
            while (runtime.milliseconds() < 1500){
                double speed = -0.3;
                leftFront.setPower(speed);
                leftBack.setPower(-speed);
                rightFront.setPower(-speed);
                rightBack.setPower(speed);
            }

            if (randomization == 0){
                strafe(22, 0.4);
            } else if (randomization == 1){
                strafe(28, 0.4);
            } else {
                strafe(34, 0.4);
            }

            runtime.reset();
            while (runtime.milliseconds() < 1700){
                double speed = -0.35;
                leftFront.setPower(speed);
                leftBack.setPower(speed);
                rightFront.setPower(speed);
                rightBack.setPower(speed);
            }
            brake();

            leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            leftTilt.setPosition(0.39);
            rightTilt.setPosition(0.54);

            leftClaw.setPosition(0.78);
            rightClaw.setPosition(0.67);

            runtime.reset();
            while (runtime.milliseconds() < 750){
                leftSlide.setPower(0.85);
                rightSlide.setPower(-0.85);
            }

            sleep(250);

            leftSlide.setPower(0.17);
            rightSlide.setPower(-0.17);

            leftTilt.setPosition(0.64);
            rightTilt.setPosition(0.31);

            sleep(750);

            leftClaw.setPosition(1.0);
            rightClaw.setPosition(0.54);

            sleep(750);

            leftTilt.setPosition(0.39);
            rightTilt.setPosition(0.54);

            sleep(250);

            runtime.reset();
            while (runtime.milliseconds() < 1250){
                leftSlide.setPower(-0.4);
                rightSlide.setPower(0.4);
            }

            leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            move(5, 0.3);

            if (randomization == 0){
                strafe(-29, 0.4);
            } else if (randomization == 1){
                strafe(-25, 0.4);
            } else {
                strafe(-31, 0.4);
            }

        }
        //visionPortal.close();
    }
    private void initTfod() {
        tfod = new TfodProcessor.Builder()
                .setModelAssetName(TFOD_MODEL_ASSET)
                .setModelLabels(LABELS)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();

        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        builder.setAutoStopLiveView(false);
        builder.addProcessor(tfod);
        visionPortal = builder.build();
        tfod.setMinResultConfidence(0.7f);

    }

    public int randDetectML() {
        int randomization;

        //initTfod();
        telemetry.addData("Status", "Getting Recognitions");
        telemetry.update();
        double x = 500; //Defaulting to right most position and the object is out of camera frame.
        runtime.reset();
        while (runtime.seconds() < 3) {
            List<Recognition> currentRecognitions  = tfod.getRecognitions();
            telemetry.addData("Status", "Recognitions Detected");
            telemetry.addData("# of Recognitions", currentRecognitions.size());
            telemetry.update();

            float recConfd = 0.0f;
            for (Recognition recognition : currentRecognitions) {
                if (recognition.getConfidence() > recConfd) {
                    x = (recognition.getLeft() + recognition.getRight()) / 2;
                    recConfd = recognition.getConfidence();
                    telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
                    telemetry.addData("- Position", "%.0f", x);
                    telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
                    telemetry.update();
                }
            }
            sleep(20);
        }

        if (x < 200){
            randomization = 0;
        } else if (x > 200 && x < 500){
            randomization = 1;
        } else {
            randomization = 2;
        }
        runtime.reset();
        while(runtime.seconds() < 3){
            telemetry.addData("- Position", "%.0f", x);
            telemetry.addData("Randomization Pattern", randomization);
            telemetry.addData("Status", "Ready");
            telemetry.update();

        }

        return randomization;
    }

    /*
    This method will detect the CTE and place the Pixel
     */
    public int randDetectDS() {

        move(18, 0.4);
        strafe(5, 0.4);

        sleep(500);

        if (rightDist.getDistance(DistanceUnit.CM) < 20){
            turn(20, 0.3);
            sleep(100);
            move(5, 0.4);
            sleep(100);
            eject();
            sleep(100);
            move(-4, 0.4);
            sleep(100);
            turn(-20, 0.3);
            return 0;
        }

        move(7, 0.4);
        if (leftDist.getDistance(DistanceUnit.CM) < 20){
            turn(-25, 0.3);
            sleep(100);
            move(3, 0.4);
            sleep(100);
            eject();
            sleep(100);
            move(-3, 100);
            sleep(100);
            turn(25, 0.3);
            sleep(100);
            move(-6, 0.4);
            return 1;
        }

        turn(-85, 0.4);
        sleep(100);
        move(9, 0.4);
        sleep(100);
        eject();
        sleep(100);
        move(-6, 0.4);
        sleep(100);
        turn(85, 0.4);
        sleep(100);
        move(-6, 0.4);

        return 2;


    }

    public void eject() {
        //Detected!
        telemetry.addData("Status", "Detected On Left; Ejecting");
        telemetry.update();

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        runtime.reset();
        while (runtime.milliseconds() < 1000) {
            double speed = -0.15;
            intake.setPower(-0.25);
            leftFront.setPower(speed);
            leftBack.setPower(speed);
            rightFront.setPower(speed);
            rightBack.setPower(speed);
        }
        brake();
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void brake(){
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        brake();
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
        brake();
        while (abs(rightSlide.getCurrentPosition()) < (turnMult * abs(angle))) {
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
        brake();
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