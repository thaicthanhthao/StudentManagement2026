# Enrollment Module API Documentation

## 34. Register Class Enrollment

### Endpoint

```http
POST /api/v1/enrollments
```

### Description

Allow a student to register for a class.

### Authorization

* STUDENT
* ADMIN

### Validation

| Field        | Validation                                    |
| ------------ | --------------------------------------------- |
| studentId    | Required                                      |
| classId      | Required                                      |
| studentId    | Must exist                                    |
| classId      | Must exist                                    |
| Enrollment   | Student has not enrolled in this class before |
| Capacity     | Current enrollment < max_students             |
| Class Status | Must be OPEN                                  |

### Request DTO

```java
public class EnrollmentCreateRequest {

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID classId;
}
```

### Request Body

```json
{
  "studentId": "stu-001",
  "classId": "cls-001"
}
```

### Response DTO

```java
public class EnrollmentResponse {

    private UUID id;
    private UUID studentId;
    private String studentCode;

    private UUID classId;
    private String classCode;

    private LocalDateTime enrollmentDate;
    private String status;
}
```

### Response

```json
{
  "success": true,
  "message": "Enrollment successful",
  "data": {
    "id": "enr-001",
    "studentId": "stu-001",
    "classId": "cls-001",
    "status": "ENROLLED"
  }
}
```

---

## 35. Cancel Enrollment

### Endpoint

```http
DELETE /api/v1/enrollments/{id}
```

### Description

Cancel a class enrollment.

### Authorization

* STUDENT
* ADMIN

### Validation

| Validation                        |
| --------------------------------- |
| Enrollment exists                 |
| Enrollment is not completed       |
| Registration period is not closed |

### Path Variable

```http
{id}
```

### Response

```json
{
  "success": true,
  "message": "Enrollment cancelled successfully"
}
```

---

## 36. Enrollment Detail

### Endpoint

```http
GET /api/v1/enrollments/{id}
```

### Description

Get enrollment detail information.

### Authorization

* STUDENT
* TEACHER
* ADMIN

### Validation

| Validation          |
| ------------------- |
| Enrollment exists   |
| User has permission |

### Response DTO

```java
public class EnrollmentDetailResponse {

    private UUID id;

    private StudentInfo student;

    private ClassInfo clazz;

    private GradeInfo grades;

    private String status;

    private LocalDateTime enrollmentDate;
}
```

### Response

```json
{
  "id":"enr-001",
  "student":{
    "id":"stu-001",
    "studentCode":"SV001",
    "fullName":"Nguyen Van A"
  },
  "class":{
    "id":"cls-001",
    "classCode":"JAVA01"
  },
  "grades":{
    "attendanceScore":8,
    "assignmentScore":9,
    "midtermScore":7,
    "finalExamScore":8
  }
}
```

---

## 37. Student Enrollment List

### Endpoint

```http
GET /api/v1/students/{id}/enrollments
```

### Description

Get all enrollments of a student.

### Authorization

* STUDENT
* ADMIN

### Validation

| Validation          |
| ------------------- |
| Student exists      |
| User has permission |

### Query Parameters

| Parameter | Type    | Required |
| --------- | ------- | -------- |
| page      | Integer | No       |
| size      | Integer | No       |
| semester  | String  | No       |

### Response DTO

```java
public class EnrollmentSummaryResponse {

    private UUID enrollmentId;
    private String classCode;
    private String className;
    private String subjectName;
    private String status;
}
```

### Response

```json
{
  "content":[
    {
      "enrollmentId":"enr-001",
      "classCode":"JAVA01",
      "subjectName":"Java Programming",
      "status":"ENROLLED"
    }
  ]
}
```

---

## 38. Check Class Capacity

### Endpoint

```http
POST /api/v1/enrollments/check-capacity
```

### Description

Check available seats before enrollment.

### Authorization

* STUDENT
* TEACHER
* ADMIN

### Validation

| Field   | Validation |
| ------- | ---------- |
| classId | Required   |
| classId | Must exist |

### Request DTO

```java
public class CapacityCheckRequest {

    @NotNull
    private UUID classId;
}
```

### Request Body

```json
{
  "classId": "cls-001"
}
```

### Response DTO

```java
public class CapacityCheckResponse {

    private UUID classId;
    private Integer maxStudents;
    private Integer currentStudents;
    private Integer remainingSlots;
    private Boolean available;
}
```

### Response

```json
{
  "classId":"cls-001",
  "maxStudents":40,
  "currentStudents":35,
  "remainingSlots":5,
  "available":true
}
```

---

## 39. Input Grades

### Endpoint

```http
PUT /api/v1/enrollments/{id}/grades
```

### Description

Teacher enters student grades.

### Authorization

* TEACHER
* ADMIN

### Validation

| Field           | Validation |
| --------------- | ---------- |
| attendanceScore | 0 - 10     |
| assignmentScore | 0 - 10     |
| midtermScore    | 0 - 10     |
| finalExamScore  | 0 - 10     |

### Request DTO

```java
public class GradeInputRequest {

    @DecimalMin("0")
    @DecimalMax("10")
    private BigDecimal attendanceScore;

    @DecimalMin("0")
    @DecimalMax("10")
    private BigDecimal assignmentScore;

    @DecimalMin("0")
    @DecimalMax("10")
    private BigDecimal midtermScore;

    @DecimalMin("0")
    @DecimalMax("10")
    private BigDecimal finalExamScore;
}
```

### Request Body

```json
{
  "attendanceScore": 8,
  "assignmentScore": 9,
  "midtermScore": 7,
  "finalExamScore": 8
}
```

### Response

```json
{
  "success": true,
  "message": "Grades entered successfully"
}
```

---

## 40. Calculate Final Score

### Endpoint

```http
POST /api/v1/enrollments/{id}/calculate-score
```

### Description

Calculate total score, letter grade and GPA point.

### Authorization

* TEACHER
* ADMIN

### Validation

| Validation                         |
| ---------------------------------- |
| Enrollment exists                  |
| All score components are available |

### Formula

```text
Attendance = 10%
Assignment = 20%
Midterm = 30%
Final Exam = 40%
```

### Response DTO

```java
public class ScoreCalculationResponse {

    private BigDecimal totalScore;
    private String letterGrade;
    private BigDecimal gradePoint;
}
```

### Response

```json
{
  "totalScore": 8.1,
  "letterGrade": "B+",
  "gradePoint": 3.5
}
```

---

## 41. Update Grades

### Endpoint

```http
PUT /api/v1/enrollments/{id}/grades
```

### Description

Update previously entered grades.

### Authorization

* TEACHER
* ADMIN

### Validation

| Validation             |
| ---------------------- |
| Enrollment exists      |
| Grade is not published |
| Teacher owns the class |

### Request DTO

```java
GradeInputRequest
```

### Response

```json
{
  "success": true,
  "message": "Grade updated successfully"
}
```

---

## 42. Class Grade Sheet

### Endpoint

```http
GET /api/v1/classes/{id}/grade-sheet
```

### Description

Get grade sheet of all students in a class.

### Authorization

* TEACHER
* ADMIN

### Validation

| Validation          |
| ------------------- |
| Class exists        |
| User has permission |

### Query Parameters

| Parameter | Type    | Required |
| --------- | ------- | -------- |
| page      | Integer | No       |
| size      | Integer | No       |

### Response DTO

```java
public class GradeSheetResponse {

    private String studentCode;
    private String fullName;

    private BigDecimal attendanceScore;
    private BigDecimal assignmentScore;
    private BigDecimal midtermScore;
    private BigDecimal finalExamScore;

    private BigDecimal totalScore;
    private String letterGrade;
}
```

### Response

```json
{
  "content":[
    {
      "studentCode":"SV001",
      "fullName":"Nguyen Van A",
      "attendanceScore":8,
      "assignmentScore":9,
      "midtermScore":7,
      "finalExamScore":8,
      "totalScore":8.1,
      "letterGrade":"B+"
    }
  ]
}
```

---

## 43. Publish Grades

### Endpoint

```http
POST /api/v1/classes/{id}/publish-grades
```

### Description

Publish official grades for a class.

### Authorization

* TEACHER
* ADMIN

### Validation

| Validation               |
| ------------------------ |
| Class exists             |
| Teacher owns the class   |
| All students have grades |

### Response DTO

```java
public class GradePublishResponse {

    private UUID classId;
    private LocalDateTime publishedAt;
    private String status;
}
```

### Response

```json
{
  "classId":"cls-001",
  "publishedAt":"2026-06-05T10:30:00",
  "status":"PUBLISHED"
}
```

---

# Role Matrix

| API                     | STUDENT | TEACHER | ADMIN |
| ----------------------- | ------- | ------- | ----- |
| Register Enrollment     | ✅       | ❌       | ✅     |
| Cancel Enrollment       | ✅       | ❌       | ✅     |
| Enrollment Detail       | ✅       | ✅       | ✅     |
| Student Enrollment List | ✅       | ❌       | ✅     |
| Check Capacity          | ✅       | ✅       | ✅     |
| Input Grades            | ❌       | ✅       | ✅     |
| Update Grades           | ❌       | ✅       | ✅     |
| Calculate Score         | ❌       | ✅       | ✅     |
| Grade Sheet             | ❌       | ✅       | ✅     |
| Publish Grades          | ❌       | ✅       | ✅     |
