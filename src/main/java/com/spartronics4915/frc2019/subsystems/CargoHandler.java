package com.spartronics4915.frc2019.subsystems;

import com.spartronics4915.lib.util.ILoop;
import com.spartronics4915.lib.util.ILooper;
import com.spartronics4915.lib.util.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CargoHandler extends Subsystem
{

    private static CargoHandler mInstance = null;

    public static CargoHandler getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new CargoHandler();
        }
        return mInstance;
    }

    public enum WantedState
    {
        DEACTIVATED, EJECT,
    }

    private enum SystemState
    {
        DEACTIVATING, EJECTING,
    }

    private WantedState mWantedState = WantedState.DEACTIVATED;
    private SystemState mSystemState = SystemState.DEACTIVATING;

    private CargoHandler()
    {
        boolean success = true;
        try
        {
            // Instantiate your hardware here
        }
        catch (Exception e)
        {
            success = false;
            logException("Couldn't instantiate hardware", e);
        }

        logInitialized(success);
    }

    private final ILoop mLoop = new ILoop()
    {

        @Override
        public void onStart(double timestamp)
        {
            synchronized (CargoHandler.this)
            {
                mWantedState = WantedState.DEACTIVATED;
                mSystemState = SystemState.DEACTIVATING;
            }
        }

        @Override
        public void onLoop(double timestamp)
        {
            synchronized (CargoHandler.this)
            {
                SystemState newState = defaultStateTransfer();
                switch (mSystemState)
                {
                    case EJECTING:
                        break;
                    case DEACTIVATING:
                        stop();
                        break;
                    default:
                        logError("Unhandled system state!");
                }
                mSystemState = newState;
            }
        }

        @Override
        public void onStop(double timestamp)
        {
            synchronized (CargoHandler.this)
            {
                stop();
            }
        }
    };

    private SystemState defaultStateTransfer()
    {
        SystemState newState = mSystemState;
        switch (mWantedState)
        {
            case DEACTIVATED:
                if(mWantedState == WantedState.EJECT)
                    newState = SystemState.EJECTING;
                else
                    newState = SystemState.DEACTIVATING;
                    break;
            case EJECT:
            if(mWantedState == WantedState.DEACTIVATED)
                newState = SystemState.DEACTIVATING;
            else
                newState = SystemState.EJECTING;
                break;
            default:
                newState = SystemState.DEACTIVATING;
                break;
        }
        return newState;
    }

    public synchronized void setWantedState(WantedState wantedState)
    {
        mWantedState = wantedState;
    }

    @Override
    public void registerEnabledLoops(ILooper enabledLooper)
    {
        enabledLooper.register(mLoop);
    }

    @Override
    public boolean checkSystem(String variant)
    {
        return false;
    }

    @Override
    public void outputTelemetry()
    {
<<<<<<< HEAD
        
=======
        SmartDashboard.putString("CargoHandler/SystemState", mSystemState.toString());
        dashboardPutString("SystemState", mSystemState.toString());
        dashboardPutState(mSystemState.toString());
>>>>>>> 1590e236f5597d12d3eee3577288992b2b8a4ed1
    }

    @Override
    public void stop()
    {
        // Stop your hardware here
    }
}
