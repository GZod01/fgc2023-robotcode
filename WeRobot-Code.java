package org.firstinspires.ftc.teamcode;
//package fr.gzod01.werobot.competition_singapour_2023;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="WeRobot: FGC2023 OpMode", group="WeRobot")
public class DualDrive extends LinearOpMode {
  private DcMotor flm;
  private DcMotor frm;
  private DcMotor brm;
  private DcMotor blm;
  private DcMotor moissonmotor;
  private DcMotor launchermotor;
  private DcMotor mountmotor;

  @Override
  public void runOpMode() {
        float x;
        double y;
        float g2x;
        boolean ismoissonactive=false;
        boolean ga;
        boolean gy;
        boolean preva =false;
        flm = hardwareMap.get(DcMotor.class, "flm");
        frm = hardwareMap.get(DcMotor.class, "frm");
        blm = hardwareMap.get(DcMotor.class, "blm");
        brm = hardwareMap.get(DcMotor.class, "brm");
        moissonmotor = hardwareMap.get(DcMotor.class, "msn");
        launchermotor = hardwareMap.get(DcMotor.class, "lnc");
        mountmotor = hardwareMap.get(DcMotor.class, "mnt");
        waitForStart();

        while (opModeIsActive()) {
            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            a = gamepad1.a;
            gy = gamepad1.y;
            if(a & !preva){
                    ismoissonactive=!ismoissonactive;
                    preva=true;
            }
            if(!a & preva){
                    preva =false;
            }
            if(ismoissonactive){
                    moissonmotor.setPower(1);
            }else{
                    moissonmotor.setPower(0);
            }
            if(y){
                    launchermotor.setPower(1);
            }else{
                    launchermotor.setPower(0);
            }
            if (gamepad1.dpad_down){
                    mountmotor.setPower(-1);
            }else if (gamepad1.dpad_up){
                    mountmotor.setPower(1);
            }else{
                    mountmotor.setPower(0);
            }


            g2x = gamepad1.right_stick_x;
            Robot.moves(x,y,flm, frm,blm,brm);
            Robot.Rotation(g2x, flm, frm, blm, brm);
        }
    }
}
public class Robot{
        public static void moves(float x, double y, DcMotor flm, DcMotor frm, DcMotor blm, DcMotor brm){
                float approx = 0.15;
                boolean xnozero = (x<-approx || x>approx);
                boolean ynozero = (y<-approx || y>approx);

                if((xnozero & ynozero) || (xnozero || ynozero)){
                        //DO TRANSLATIONS
                        if(xnozero){
                                if(ynozzero){
                                        Robot.diagonal(x,y, flm,frm,blm,brm);
                                }else{
                                        Robot.horizontal(x,flm,frm,blm,brm);
                                }
                        }else{
                                Robot.vertical(y,flm,frm,blm,brm);
                        }
                }
                else{
                        // NO TRANSLATIONS BECAUSE 0 SO STOP
                        flm.setPower(0);
                        frm.setPower(0);
                        blm.setPower(0);
                        brm.setPower(0);
                }

        }
        public static void diagonal(float x, double y, DcMotor flm, DcMotor frm,DcMotor blm,DcMotor brm){
                if(x>0){
                        if(y>0){
                                // North East
                                Robot.movement(flm,frm,blm,brm,{1,0,0,1});

                        }else{
                                //South East
                                Robot.movement(flm,frm,blm,brm,{0,-1,-1,0});
                        }
                }else{
                        if(y>0){
                                //North West
                                Robot.movement(flm,frm,blm,brm,{0,1,1,0});
                        }
                        else{
                                //South West
                                Robot.movement(flm,frm,blm,brm,{-1,0,0,-1});
                        }
                }
        }
        public static void horizontal(float x,DcMotor flm,DcMotor frm,DcMotor blm,DcMotor brm){
                if(x>0){
                        Robot.movement(flm,frm,blm,brm,{1,-1,-1,1});
                }else{
                        Robot.movement(flm,frm,blm,brm,;{-1,1,1,-1});
                }



        }
        public static void vertical(double y,DcMotor flm,DcMotor frm,DcMotor blm,DcMotor brm){
                if(y>0){
                        Robot.movement(flm,frm,blm,brm,{1,1,1,1});
                }else{
                        Robot.movement(flm,frm,blm,brm,{-1,-1,-1,-1});
                }

        }
        public static void movement(DcMotor flm,DcMotor frm,DcMotor blm,DcMotor brm,int[] list){
                flm.setPower(list[0]);
                frm.setPower(list[1]);
                blm.setPower(list[2]);
                brm.setPower(list[3]);
        }
        public static void Rotations(float g2x, DcMotor flm,DcMotor frm,DcMotor blm,DcMotor brm){
                float approx = 0.15;
                boolean xnozero = (g2x<-approx || g2x>approx);
                if(xnozero){
                        if(x>0){
                                Robot.movement(flm,frm,blm,brm,{1,-1,1,-1});
                        }else{
                                Robot.movement(flm,frm,blm,brm,{-1,1,-1,1});
                        }
                }
        }


}