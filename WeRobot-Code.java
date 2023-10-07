package org.firstinspires.ftc.teamcode;
//package fr.gzod01.werobot.competition_singapour_2023;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="WeRobot: FGC2023", group="WeRobot")
public class WeRobot_FGC2023 extends LinearOpMode {
        private DcMotor flm;
        private DcMotor frm;
        private DcMotor brm;
        private DcMotor blm;
        // OTHER THAN MOVES:
        // lanceur = panier
        private DcMotorEx panier1 = null;
        private DcMotorEx panier2 = null;
        private DcMotor moissoneuse = null;
        private DcMotor accroche = null;
        private Servo servoAccroche = null;
        private Servo servoPanier = null;
        private double puissanceMoissoneuse = 0;
        private boolean panierEnRoute = false;
        private boolean dejavu = false;
        private boolean dejavulb = false;
        private boolean dejavudl = false;
        private boolean dejavudr = false;
        private boolean modeFrein = false;
        private boolean moissoneuseEnRoute = false;
        private boolean servoAccrocheEnRoute = true;
        private boolean dejavuM = false;
        private boolean dejavuA = false;
        private boolean dejavudown = false;
        private boolean panierDescendu = false;
        private double puissancePanier1 = 0;
        private double puissancePanier2 = 0;
        private int targetPos;
        private Long targetPosLong;

        @Override
        public void runOpMode() throws InterruptedException {
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
                panier1 = (DcMotorEx) hardwareMap.get(DcMotorEx.class, "pn1");
                panier2 = (DcMotorEx) hardwareMap.get(DcMotorEx.class, "pn2");
                
                panier2.setDirection(DcMotorSimple.Direction.REVERSE);
                
                panier1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                panier2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                
                moissoneuse = hardwareMap.get(DcMotor.class, "msn");
                accroche = hardwareMap.get(DcMotor.class,"mnt");
                servoAccroche = hardwareMap.get(Servo.class, "servomnt");
                servoPanier = hardwareMap.get(Servo.class, "servobascule");
                accroche.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                WRRobot wr = new WRRobot(flm,frm,blm,brm,gamepad1);
                servoPanier.setPosition(0.5);
                servoAccroche.setPosition(0.5);
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
                        if (gamepad1.dpad_up){
                                if (!dejavu){
                                        // tick par seconde
                                        
                                        // panier1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                        // panier2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                                        
                                        panier1.setVelocity(600);
                                        panier2.setVelocity(600);
                                        
                                        // 288 ticks par tour
                                        targetPosLong = (Long) Math.round(288*8.5);
                                        telemetry.addData("Long Pos : ", targetPosLong);
                                        
                                        targetPos = targetPosLong.intValue();
                                        panier1.setTargetPosition(targetPos);
                                        panier2.setTargetPosition(targetPos);
                                        panier2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                        panier1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                        // while (panier1.isBusy()){
                                        //         targetPos = panier1.getCurrentPosition();
                                                        
                                        
                                        // }
                                        
                                        telemetry.addData("New code :","isOK");
                                        
                                        
                                        
                                        panierEnRoute=!panierEnRoute;
                                        dejavu=true;
                                }
                        }
                        else{
                                dejavu=false;
                        }
                        if (gamepad1.dpad_down){
                              if (!dejavudown){
                                panier1.setVelocity(75);
                                panier2.setVelocity(75);
                                targetPosLong = (Long) Math.round(288*2.5);
                                targetPos = targetPosLong.intValue();
                                panier1.setTargetPosition(targetPos);
                                panier2.setTargetPosition(targetPos);
                                dejavudown = true;
                              }  
                        }
                        else{
                                dejavudown = false;
                        }
                        
                        if (gamepad1.left_bumper){
                                if(!dejavulb){
                                        double[] positions_to_go = {0.5,0.6,0.7,0.8,0.825,0.85,0.875,0.9,0.95,1};
                                        int[] times_to_sleep = { 100,100,100,200,200,200,200,200,200,200,200,200,200,200};
                                        for(int i= 0; i < positions_to_go.length; i++){
                                                Thread.sleep(times_to_sleep[i]);
                                                servoPanier.setPosition(positions_to_go[i]);
                                        }
                                        dejavulb = true;
                                }
                                dejavulb = false;
                        }
                        
                        //boutton rose
                        if(gamepad1.x){
                                if(!dejavudl){
                                        servoPanier.setPosition(0.37);
                                        dejavudl = true;
                                }
                                dejavudl = false;
                        }
                        
                        if (gamepad1.right_bumper){
                                if (!dejavuA){
                                        if (servoAccrocheEnRoute){
                                                /*double[] p_t_g = {0.55,0.5,0.45,0.4,0.35,0.3,0.275,0.2625,0.25,0.24};
                                                int[] t_t_s = {100,100,100,100,100,100,300,300,200,100};
                                                for(int i = 0; i< p_t_g.length; i++){
                                                        Thread.sleep(t_t_s[i]);
                                                        servoAccroche.setPosition(p_t_g[i]);
                                                        telemetry.addData("puissance servo",servoAccroche.getPosition());
                                                }*/
                                                servoAccroche.setPosition(0.15);
                                                
                                        }
                                        else {
                                                /*double[] p_t_g = {0.3,0.325,0.35,0.375,0.4,0.425,0.45,0.5};
                                                int[] t_t_s = {100,100,100,100,100,200,200,100};
                                                for(int i = 0; i< p_t_g.length; i++){
                                                        Thread.sleep(t_t_s[i]);
                                                        servoAccroche.setPosition(p_t_g[i]);
                                                }*/
                                                servoAccroche.setPosition(0.5);
                                        }
                                        servoAccrocheEnRoute=!servoAccrocheEnRoute;
                                        dejavuA = true;
                                }
                        }
                        else{
                                dejavuA = false;
                        }
                        //bouton O
                        if (gamepad1.b){
                                if (!dejavuM){
                                        if (moissoneuseEnRoute){
                                                moissoneuse.setPower(-0.9);
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
                        // bouton vert
                        if (gamepad1.a){
                                accroche.setPower(-0.8);
                        }
                        // bouton X
                        else if (gamepad1.y){
                                accroche.setPower(0.8);
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
                        puissancePanier1 = panier1.getPower();
                        puissancePanier2 = panier2.getPower();
                        telemetry.addData("puissance moisso", puissanceMoissoneuse);
                        telemetry.addData("angle servo", servoAccroche.getPosition());
                        telemetry.addData("dejavu", dejavu);
                        telemetry.addData("puissance panier1", puissancePanier1);
                        telemetry.addData("puissance panier2", puissancePanier2);
                        telemetry.addData("TUTORIEL","TUTORIEL");
                        telemetry.addData("Stick Gauche","Translations");
                        telemetry.addData("Stick Droit","Rotations");
                        telemetry.addData("X","Descendre Crochet");
                        telemetry.addData("Rond","Moissoneuse");
                }
        }
}
class WRRobot{
        private DcMotor flm;
        private DcMotor frm;
        private DcMotor blm;
        private DcMotor brm;
        private Gamepad gamepad1;
        public WRRobot(DcMotor cflm,DcMotor cfrm,DcMotor cblm,DcMotor cbrm,Gamepad gamepad1){
                flm = cflm;
                frm = cfrm;
                blm = cblm;
                brm = cbrm;
                this.gamepad1 = gamepad1;
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
                double pow = gamepad1.left_trigger > 0.5?gamepad1.left_trigger:(gamepad1.right_trigger>0.5?0.2:0.4);
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
                double pow = gamepad1.left_trigger > 0.5?gamepad1.left_trigger:(gamepad1.right_trigger>0.5?0.2:0.4);
                if(x>0){
                        movement(new double[]{pow,pow,-pow,-pow});
                }else{
                        movement(new double[]{-pow,-pow,pow,pow});
                }



        }
        public  void vertical(double y){
                double pow = gamepad1.left_trigger > 0.5?gamepad1.left_trigger:(gamepad1.right_trigger>0.5?0.2:0.4);
                if(y>0){
                        movement(new double[]{pow,-pow,pow,-pow*1});
                }else{
                        movement(new double[]{-pow,pow,-pow,pow*1});
                }

        }
        public void movement(double[] list){
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
                double pow = gamepad1.left_trigger > 0.5?gamepad1.left_trigger:(gamepad1.right_trigger>0.5?0.2:0.4);
                if (!xnozero) return;
                if (g2x > 0) {
                        movement(new double[]{pow,pow,pow,pow});
                } else {
                        movement(new double[]{-pow,-pow,-pow,-pow});
                }
        }
}
