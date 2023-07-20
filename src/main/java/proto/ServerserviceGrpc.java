package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: services.proto")
public final class ServerserviceGrpc {

  private ServerserviceGrpc() {}

  public static final String SERVICE_NAME = "Serverservice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.Services.ExamsRequest,
      proto.Services.ExamsReply> getAvailableExamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "available_exams",
      requestType = proto.Services.ExamsRequest.class,
      responseType = proto.Services.ExamsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.Services.ExamsRequest,
      proto.Services.ExamsReply> getAvailableExamsMethod() {
    io.grpc.MethodDescriptor<proto.Services.ExamsRequest, proto.Services.ExamsReply> getAvailableExamsMethod;
    if ((getAvailableExamsMethod = ServerserviceGrpc.getAvailableExamsMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getAvailableExamsMethod = ServerserviceGrpc.getAvailableExamsMethod) == null) {
          ServerserviceGrpc.getAvailableExamsMethod = getAvailableExamsMethod = 
              io.grpc.MethodDescriptor.<proto.Services.ExamsRequest, proto.Services.ExamsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "available_exams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ExamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ExamsReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("available_exams"))
                  .build();
          }
        }
     }
     return getAvailableExamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.BookingRequest,
      proto.Services.BookingReply> getExamBookingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "exam_booking",
      requestType = proto.Services.BookingRequest.class,
      responseType = proto.Services.BookingReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Services.BookingRequest,
      proto.Services.BookingReply> getExamBookingMethod() {
    io.grpc.MethodDescriptor<proto.Services.BookingRequest, proto.Services.BookingReply> getExamBookingMethod;
    if ((getExamBookingMethod = ServerserviceGrpc.getExamBookingMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getExamBookingMethod = ServerserviceGrpc.getExamBookingMethod) == null) {
          ServerserviceGrpc.getExamBookingMethod = getExamBookingMethod = 
              io.grpc.MethodDescriptor.<proto.Services.BookingRequest, proto.Services.BookingReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "exam_booking"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.BookingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.BookingReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("exam_booking"))
                  .build();
          }
        }
     }
     return getExamBookingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.StartRequest,
      proto.Services.StartReply> getStartExamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "start_exam",
      requestType = proto.Services.StartRequest.class,
      responseType = proto.Services.StartReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Services.StartRequest,
      proto.Services.StartReply> getStartExamMethod() {
    io.grpc.MethodDescriptor<proto.Services.StartRequest, proto.Services.StartReply> getStartExamMethod;
    if ((getStartExamMethod = ServerserviceGrpc.getStartExamMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getStartExamMethod = ServerserviceGrpc.getStartExamMethod) == null) {
          ServerserviceGrpc.getStartExamMethod = getStartExamMethod = 
              io.grpc.MethodDescriptor.<proto.Services.StartRequest, proto.Services.StartReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "start_exam"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.StartRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.StartReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("start_exam"))
                  .build();
          }
        }
     }
     return getStartExamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.NextQuestionRequest,
      proto.Services.NextQuestionReply> getNextQuestionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "next_question",
      requestType = proto.Services.NextQuestionRequest.class,
      responseType = proto.Services.NextQuestionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Services.NextQuestionRequest,
      proto.Services.NextQuestionReply> getNextQuestionMethod() {
    io.grpc.MethodDescriptor<proto.Services.NextQuestionRequest, proto.Services.NextQuestionReply> getNextQuestionMethod;
    if ((getNextQuestionMethod = ServerserviceGrpc.getNextQuestionMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getNextQuestionMethod = ServerserviceGrpc.getNextQuestionMethod) == null) {
          ServerserviceGrpc.getNextQuestionMethod = getNextQuestionMethod = 
              io.grpc.MethodDescriptor.<proto.Services.NextQuestionRequest, proto.Services.NextQuestionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "next_question"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.NextQuestionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.NextQuestionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("next_question"))
                  .build();
          }
        }
     }
     return getNextQuestionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.AnswerRequest,
      proto.Services.AnswerReply> getSubmitAnswerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "submit_answer",
      requestType = proto.Services.AnswerRequest.class,
      responseType = proto.Services.AnswerReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Services.AnswerRequest,
      proto.Services.AnswerReply> getSubmitAnswerMethod() {
    io.grpc.MethodDescriptor<proto.Services.AnswerRequest, proto.Services.AnswerReply> getSubmitAnswerMethod;
    if ((getSubmitAnswerMethod = ServerserviceGrpc.getSubmitAnswerMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getSubmitAnswerMethod = ServerserviceGrpc.getSubmitAnswerMethod) == null) {
          ServerserviceGrpc.getSubmitAnswerMethod = getSubmitAnswerMethod = 
              io.grpc.MethodDescriptor.<proto.Services.AnswerRequest, proto.Services.AnswerReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "submit_answer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.AnswerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.AnswerReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("submit_answer"))
                  .build();
          }
        }
     }
     return getSubmitAnswerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.ResultRequest,
      proto.Services.ResultReply> getResultMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "result",
      requestType = proto.Services.ResultRequest.class,
      responseType = proto.Services.ResultReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.Services.ResultRequest,
      proto.Services.ResultReply> getResultMethod() {
    io.grpc.MethodDescriptor<proto.Services.ResultRequest, proto.Services.ResultReply> getResultMethod;
    if ((getResultMethod = ServerserviceGrpc.getResultMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getResultMethod = ServerserviceGrpc.getResultMethod) == null) {
          ServerserviceGrpc.getResultMethod = getResultMethod = 
              io.grpc.MethodDescriptor.<proto.Services.ResultRequest, proto.Services.ResultReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "result"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ResultRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ResultReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("result"))
                  .build();
          }
        }
     }
     return getResultMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.Services.ModuleRequest,
      proto.Services.ModuleReply> getCorrectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "correction",
      requestType = proto.Services.ModuleRequest.class,
      responseType = proto.Services.ModuleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.Services.ModuleRequest,
      proto.Services.ModuleReply> getCorrectionMethod() {
    io.grpc.MethodDescriptor<proto.Services.ModuleRequest, proto.Services.ModuleReply> getCorrectionMethod;
    if ((getCorrectionMethod = ServerserviceGrpc.getCorrectionMethod) == null) {
      synchronized (ServerserviceGrpc.class) {
        if ((getCorrectionMethod = ServerserviceGrpc.getCorrectionMethod) == null) {
          ServerserviceGrpc.getCorrectionMethod = getCorrectionMethod = 
              io.grpc.MethodDescriptor.<proto.Services.ModuleRequest, proto.Services.ModuleReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "Serverservice", "correction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ModuleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.Services.ModuleReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ServerserviceMethodDescriptorSupplier("correction"))
                  .build();
          }
        }
     }
     return getCorrectionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerserviceStub newStub(io.grpc.Channel channel) {
    return new ServerserviceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerserviceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ServerserviceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerserviceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ServerserviceFutureStub(channel);
  }

  /**
   */
  public static abstract class ServerserviceImplBase implements io.grpc.BindableService {

    /**
     */
    public void availableExams(proto.Services.ExamsRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ExamsReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAvailableExamsMethod(), responseObserver);
    }

    /**
     */
    public void examBooking(proto.Services.BookingRequest request,
        io.grpc.stub.StreamObserver<proto.Services.BookingReply> responseObserver) {
      asyncUnimplementedUnaryCall(getExamBookingMethod(), responseObserver);
    }

    /**
     */
    public void startExam(proto.Services.StartRequest request,
        io.grpc.stub.StreamObserver<proto.Services.StartReply> responseObserver) {
      asyncUnimplementedUnaryCall(getStartExamMethod(), responseObserver);
    }

    /**
     */
    public void nextQuestion(proto.Services.NextQuestionRequest request,
        io.grpc.stub.StreamObserver<proto.Services.NextQuestionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getNextQuestionMethod(), responseObserver);
    }

    /**
     */
    public void submitAnswer(proto.Services.AnswerRequest request,
        io.grpc.stub.StreamObserver<proto.Services.AnswerReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSubmitAnswerMethod(), responseObserver);
    }

    /**
     */
    public void result(proto.Services.ResultRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ResultReply> responseObserver) {
      asyncUnimplementedUnaryCall(getResultMethod(), responseObserver);
    }

    /**
     */
    public void correction(proto.Services.ModuleRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ModuleReply> responseObserver) {
      asyncUnimplementedUnaryCall(getCorrectionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAvailableExamsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.Services.ExamsRequest,
                proto.Services.ExamsReply>(
                  this, METHODID_AVAILABLE_EXAMS)))
          .addMethod(
            getExamBookingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Services.BookingRequest,
                proto.Services.BookingReply>(
                  this, METHODID_EXAM_BOOKING)))
          .addMethod(
            getStartExamMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Services.StartRequest,
                proto.Services.StartReply>(
                  this, METHODID_START_EXAM)))
          .addMethod(
            getNextQuestionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Services.NextQuestionRequest,
                proto.Services.NextQuestionReply>(
                  this, METHODID_NEXT_QUESTION)))
          .addMethod(
            getSubmitAnswerMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Services.AnswerRequest,
                proto.Services.AnswerReply>(
                  this, METHODID_SUBMIT_ANSWER)))
          .addMethod(
            getResultMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.Services.ResultRequest,
                proto.Services.ResultReply>(
                  this, METHODID_RESULT)))
          .addMethod(
            getCorrectionMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.Services.ModuleRequest,
                proto.Services.ModuleReply>(
                  this, METHODID_CORRECTION)))
          .build();
    }
  }

  /**
   */
  public static final class ServerserviceStub extends io.grpc.stub.AbstractStub<ServerserviceStub> {
    private ServerserviceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerserviceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerserviceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerserviceStub(channel, callOptions);
    }

    /**
     */
    public void availableExams(proto.Services.ExamsRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ExamsReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getAvailableExamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void examBooking(proto.Services.BookingRequest request,
        io.grpc.stub.StreamObserver<proto.Services.BookingReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getExamBookingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void startExam(proto.Services.StartRequest request,
        io.grpc.stub.StreamObserver<proto.Services.StartReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartExamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void nextQuestion(proto.Services.NextQuestionRequest request,
        io.grpc.stub.StreamObserver<proto.Services.NextQuestionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getNextQuestionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void submitAnswer(proto.Services.AnswerRequest request,
        io.grpc.stub.StreamObserver<proto.Services.AnswerReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubmitAnswerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void result(proto.Services.ResultRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ResultReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getResultMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void correction(proto.Services.ModuleRequest request,
        io.grpc.stub.StreamObserver<proto.Services.ModuleReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getCorrectionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServerserviceBlockingStub extends io.grpc.stub.AbstractStub<ServerserviceBlockingStub> {
    private ServerserviceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerserviceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerserviceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerserviceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.Services.ExamsReply> availableExams(
        proto.Services.ExamsRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getAvailableExamsMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Services.BookingReply examBooking(proto.Services.BookingRequest request) {
      return blockingUnaryCall(
          getChannel(), getExamBookingMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Services.StartReply startExam(proto.Services.StartRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartExamMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Services.NextQuestionReply nextQuestion(proto.Services.NextQuestionRequest request) {
      return blockingUnaryCall(
          getChannel(), getNextQuestionMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Services.AnswerReply submitAnswer(proto.Services.AnswerRequest request) {
      return blockingUnaryCall(
          getChannel(), getSubmitAnswerMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.Services.ResultReply result(proto.Services.ResultRequest request) {
      return blockingUnaryCall(
          getChannel(), getResultMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<proto.Services.ModuleReply> correction(
        proto.Services.ModuleRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getCorrectionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServerserviceFutureStub extends io.grpc.stub.AbstractStub<ServerserviceFutureStub> {
    private ServerserviceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServerserviceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerserviceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServerserviceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Services.BookingReply> examBooking(
        proto.Services.BookingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getExamBookingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Services.StartReply> startExam(
        proto.Services.StartRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartExamMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Services.NextQuestionReply> nextQuestion(
        proto.Services.NextQuestionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getNextQuestionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Services.AnswerReply> submitAnswer(
        proto.Services.AnswerRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSubmitAnswerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.Services.ResultReply> result(
        proto.Services.ResultRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getResultMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AVAILABLE_EXAMS = 0;
  private static final int METHODID_EXAM_BOOKING = 1;
  private static final int METHODID_START_EXAM = 2;
  private static final int METHODID_NEXT_QUESTION = 3;
  private static final int METHODID_SUBMIT_ANSWER = 4;
  private static final int METHODID_RESULT = 5;
  private static final int METHODID_CORRECTION = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerserviceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerserviceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AVAILABLE_EXAMS:
          serviceImpl.availableExams((proto.Services.ExamsRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.ExamsReply>) responseObserver);
          break;
        case METHODID_EXAM_BOOKING:
          serviceImpl.examBooking((proto.Services.BookingRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.BookingReply>) responseObserver);
          break;
        case METHODID_START_EXAM:
          serviceImpl.startExam((proto.Services.StartRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.StartReply>) responseObserver);
          break;
        case METHODID_NEXT_QUESTION:
          serviceImpl.nextQuestion((proto.Services.NextQuestionRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.NextQuestionReply>) responseObserver);
          break;
        case METHODID_SUBMIT_ANSWER:
          serviceImpl.submitAnswer((proto.Services.AnswerRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.AnswerReply>) responseObserver);
          break;
        case METHODID_RESULT:
          serviceImpl.result((proto.Services.ResultRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.ResultReply>) responseObserver);
          break;
        case METHODID_CORRECTION:
          serviceImpl.correction((proto.Services.ModuleRequest) request,
              (io.grpc.stub.StreamObserver<proto.Services.ModuleReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServerserviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerserviceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Serverservice");
    }
  }

  private static final class ServerserviceFileDescriptorSupplier
      extends ServerserviceBaseDescriptorSupplier {
    ServerserviceFileDescriptorSupplier() {}
  }

  private static final class ServerserviceMethodDescriptorSupplier
      extends ServerserviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerserviceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServerserviceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerserviceFileDescriptorSupplier())
              .addMethod(getAvailableExamsMethod())
              .addMethod(getExamBookingMethod())
              .addMethod(getStartExamMethod())
              .addMethod(getNextQuestionMethod())
              .addMethod(getSubmitAnswerMethod())
              .addMethod(getResultMethod())
              .addMethod(getCorrectionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
