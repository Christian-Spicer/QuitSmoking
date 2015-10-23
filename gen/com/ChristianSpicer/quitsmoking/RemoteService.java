/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Christian\\workspace\\QuitSmoking\\src\\com\\ChristianSpicer\\quitsmoking\\RemoteService.aidl
 */
package com.ChristianSpicer.quitsmoking;
public interface RemoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.ChristianSpicer.quitsmoking.RemoteService
{
private static final java.lang.String DESCRIPTOR = "com.ChristianSpicer.quitsmoking.RemoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.ChristianSpicer.quitsmoking.RemoteService interface,
 * generating a proxy if needed.
 */
public static com.ChristianSpicer.quitsmoking.RemoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.ChristianSpicer.quitsmoking.RemoteService))) {
return ((com.ChristianSpicer.quitsmoking.RemoteService)iin);
}
return new com.ChristianSpicer.quitsmoking.RemoteService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_fromServiceTrue:
{
data.enforceInterface(DESCRIPTOR);
this.fromServiceTrue();
reply.writeNoException();
return true;
}
case TRANSACTION_fromServiceFalse:
{
data.enforceInterface(DESCRIPTOR);
this.fromServiceFalse();
reply.writeNoException();
return true;
}
case TRANSACTION_fromServiceCountDownTimer:
{
data.enforceInterface(DESCRIPTOR);
this.fromServiceCountDownTimer();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.ChristianSpicer.quitsmoking.RemoteService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void fromServiceTrue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_fromServiceTrue, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void fromServiceFalse() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_fromServiceFalse, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void fromServiceCountDownTimer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_fromServiceCountDownTimer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_fromServiceTrue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_fromServiceFalse = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_fromServiceCountDownTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void fromServiceTrue() throws android.os.RemoteException;
public void fromServiceFalse() throws android.os.RemoteException;
public void fromServiceCountDownTimer() throws android.os.RemoteException;
}
