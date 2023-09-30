package org.firstinspires.ftc.teamcode;
//package fr.gzod01.werobot.competition_singapour_2023;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="WeRobot: FGC2023", group="WeRobot")
public class WeRobot_FGC2023 extends LinearOpMode {
        private DcMotor flm;
        private DcMotor frm;
        private DcMotor brm;
        private DcMotor blm;
        // OTHER THAN MOVES:
        // lanceur = panier
        private DcMotor lanceur = null;
        private DcMotor moissoneuse = null;
        private DcMotor accroche = null;
        private double puissanceMoissoneuse = 0;
        private boolean lanceurEnRoute = false;
        private boolean dejavu = false;
        private boolean modeFrein = false;
        private boolean moissoneuseEnRoute = false;
        private boolean dejavuM = false;

        @Override
        public void runOpMode() {
                float x;
                double y;
                float g2x;
                telemetry.addData("Status", "Initialized");
                telemetry.update();
                flm = hardwareMap.get(DcMotor.class, "flm");
                frm = hardwareMap.get(DcMotor.class, "frm");
                blm = hardwareMap.get(DcMotor.class, "blm");
                brm = hardwareMap.get(DcMotor.class, "brm");
                // OTHER THAN MOVES:
                lanceur = hardwareMap.get(DcMotor.class, "lnc");
                moissoneuse = hardwareMap.get(DcMotor.class, "msn");
                accroche = hardwareMap.get(DcMotor.class,"mnt");
                accroche.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                WRRobot wr = new WRRobot(flm,frm,blm,brm);
                waitForStart();

                while (opModeIsActive()) {
                        telemetry.addData("Status", "Running");
                        telemetry.update();
                        x = gamepad1.left_stick_x;
                        y = -gamepad1.left_stick_y;
                        g2x = gamepad1.right_stick_x;
                        wr.moves(x,y );
                        wr.Rotation(g2x );
                        // OTHER THAN MOVES:
                        //             // bouton O
                        if (gamepad1.b){
                                if (!dejavu){
                                        if (lanceurEnRoute){
                                                lanceur.setPower(0.8);
                                        }
                                        else{
                                                lanceur.setPower(0);
                                        }
                                        lanceurEnRoute=!lanceurEnRoute;
                                        dejavu=true;
                                }
                        }
                        else{
                                dejavu = false;
                        }
                        //bouton X
                        if (gamepad1.a){
                                if (!dejavuM){
                                        if (moissoneuseEnRoute){
                                                moissoneuse.setPower(-1);
                                        }
                                              else{
                                                moissoneuse.setPower(0);
                                        }
                                        moissoneuseEnRoute=!moissoneuseEnRoute;
                                        dejavuM=true;
                                }
                        }
                        else{
                                dejavuM = false;
                        }
                        if (gamepad1.right_bumper){
                                accroche.setPower(0.8);
                        }
                        else if (gamepad1.left_bumper)
                        {
                                accroche.setPower(-0.8);
                        }
                        else {
                                if (!modeFrein){
                                        accroche.setPower(0);
                                }
                        }
                        if (gamepad1.ps){
                                modeFrein = true;
                                accroche.setPower(0.17);
                        }
                        puissanceMoissoneuse = moissoneuse.getPower();
                        telemetry.addData("puissance moisso : ", puissanceMoissoneuse);
                        telemetry.addData("dejavu :", dejavu);
                }
        }
}
class WRRobot{
        private DcMotor flm;
        private DcMotor frm;
        private DcMotor blm;
        private DcMotor brm;
        public WRRobot(DcMotor cflm,DcMotor cfrm,DcMotor cblm,DcMotor cbrm){
                flm = cflm;
                frm = cfrm;
                blm = cblm;
                brm = cbrm;
        }
        public  void moves(float x, double y){
                double approx = 0.15;
                boolean xnozero = (x<-approx || x>approx);
                boolean ynozero = (y<-approx || y>approx);

                if(!(Math.abs(x)>approx || Math.abs(y)>approx)){
                        flm.setPower(0);
                        frm.setPower(0);
                        blm.setPower(0);
                        brm.setPower(0);
                        return;
                }

                //DO TRANSLATIONS
                if(Math.abs(x)>approx){
                        if(Math.abs(y)>approx){
                                diagonal(x,y);
                        }else{
                                horizontal(x);
                        }
                }else{
                        vertical(y);
                }

        }
        public void diagonal(float x, double y){
                double pow = Math.abs(x+y)/2;
                if(x>0){
                        if(y>0){
                                // North East
                                movement(new double[]{pow,0,0,-pow});

                        }else{
                                //South East
                                movement(new double[]{0,pow,-pow,0});
                        }
                }else{
                        if(y>0){
                                //North West
                                movement(new double[]{0,-pow,pow,0});
                        }
                        else{
                                //South West
                                movement(new double[]{-pow,0,0,pow});
                        }
                }
        }
        public  void horizontal(float x){
                double pow = Math.abs(x);
                if(x>0){
                        movement(new double[]{pow,pow,-pow,-pow});
                }else{
                        movement(new double[]{-pow,-pow,pow,pow});
                }



        }
        public  void vertical(double y){
                double pow = Math.abs(y);
                if(y>0){
                        movement(new double[]{pow,-pow,pow,-pow});
                }else{
                        movement(new double[]{-pow,pow,-pow,pow});
                }

        }
        public  void movement(double[] list){
                flm.setPower(list[0]);
                frm.setPower(list[1]);
                blm.setPower(list[2]);
                brm.setPower(list[3]);
        }

        /*
         * @param g2x is the x coordinates of the right joystick
         */
        public  void Rotation(float g2x){
                double approx = 0.15;
                boolean xnozero = (g2x<-approx || g2x>approx);
                double pow = Math.abs(g2x);
                if (!xnozero) return;
                if (g2x > 0) {
                        movement(new double[]{pow,pow,pow,pow});
                } else {
                        movement(new double[]{-pow,-pow,-pow,pow});
                }
        }


}

