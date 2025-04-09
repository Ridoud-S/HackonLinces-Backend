// lib/models/evaluation.dart
import 'package:hive/hive.dart';

part 'evaluation.g.dart';

@HiveType(typeId: 0)
class Evaluation {
  @HiveField(0)
  String? id;

  @HiveField(1)
  String evaluatorName;

  @HiveField(2)
  String evaluatorId;

  @HiveField(3)
  String evaluatedName;

  @HiveField(4)
  String evaluatedId;

  @HiveField(5)
  String flightNumber;

  @HiveField(6)
  String destination;

  @HiveField(7)
  DateTime evaluationDate;

  @HiveField(8)
  int score;

  @HiveField(9)
  List<String> evaluatedAreas;

  @HiveField(10)
  String status; // "Aprobado", "En evaluación", etc.

  @HiveField(11)
  bool isSynced; // Indica si ya fue sincronizado con el backend

  Evaluation({
    this.id,
    required this.evaluatorName,
    required this.evaluatorId,
    required this.evaluatedName,
    required this.evaluatedId,
    required this.flightNumber,
    required this.destination,
    required this.evaluationDate,
    required this.score,
    required this.evaluatedAreas,
    required this.status,
    this.isSynced = false,
  });

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'evaluatorName': evaluatorName,
      'evaluatorId': evaluatorId,
      'evaluatedName': evaluatedName,
      'evaluatedId': evaluatedId,
      'flightNumber': flightNumber,
      'destination': destination,
      'evaluationDate': evaluationDate.toIso8601String(),
      'score': score,
      'evaluatedAreas': evaluatedAreas,
      'status': status,
    };
  }

  static Evaluation fromJson(Map<String, dynamic> json) {
    return Evaluation(
      id: json['id'],
      evaluatorName: json['evaluatorName'],
      evaluatorId: json['evaluatorId'],
      evaluatedName: json['evaluatedName'],
      evaluatedId: json['evaluatedId'],
      flightNumber: json['flightNumber'],
      destination: json['destination'],
      evaluationDate: DateTime.parse(json['evaluationDate']),
      score: json['score'],
      evaluatedAreas: List<String>.from(json['evaluatedAreas']),
      status: json['status'],
    );
  }
}

// Generated automatically
class EvaluationAdapter extends TypeAdapter<Evaluation> {
  @override
  final int typeId = 0;

  @override
  Evaluation read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return Evaluation(
      id: fields[0] as String?,
      evaluatorName: fields[1] as String,
      evaluatorId: fields[2] as String,
      evaluatedName: fields[3] as String,
      evaluatedId: fields[4] as String,
      flightNumber: fields[5] as String,
      destination: fields[6] as String,
      evaluationDate: fields[7] as DateTime,
      score: fields[8] as int,
      evaluatedAreas: (fields[9] as List).cast<String>(),
      status: fields[10] as String,
      isSynced: fields[11] as bool,
    );
  }

  @override
  void write(BinaryWriter writer, Evaluation obj) {
    writer
      ..writeByte(12)
      ..writeByte(0)
      ..write(obj.id)
      ..writeByte(1)
      ..write(obj.evaluatorName)
      ..writeByte(2)
      ..write(obj.evaluatorId)
      ..writeByte(3)
      ..write(obj.evaluatedName)
      ..writeByte(4)
      ..write(obj.evaluatedId)
      ..writeByte(5)
      ..write(obj.flightNumber)
      ..writeByte(6)
      ..write(obj.destination)
      ..writeByte(7)
      ..write(obj.evaluationDate)
      ..writeByte(8)
      ..write(obj.score)
      ..writeByte(9)
      ..write(obj.evaluatedAreas)
      ..writeByte(10)
      ..write(obj.status)
      ..writeByte(11)
      ..write(obj.isSynced);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
          other is EvaluationAdapter &&
              runtimeType == other.runtimeType &&
              typeId == other.typeId;
}