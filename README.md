# java-test-utils

<a href="https://sdkman.io/jdks"><img alt="Java" src="https://img.shields.io/badge/-java-orange?logo=java&logoColor=white"></a>
<a href="https://github.com/sauljabin/java-test-utils"><img alt="GitHub" src="https://badges.pufler.dev/updated/sauljabin/java-test-utils?label=updated"></a>
<a href="https://github.com/sauljabin/java-test-utils/blob/main/LICENSE"><img alt="MIT License" src="https://img.shields.io/github/license/sauljabin/java-test-utils"></a>
<a href="https://github.com/sauljabin/java-test-utils/actions"><img alt="GitHub Actions" src="https://img.shields.io/github/checks-status/sauljabin/java-test-utils/main?label=tests"></a>
<a href="https://app.codecov.io/gh/sauljabin/java-test-utils"><img alt="Codecov" src="https://img.shields.io/codecov/c/github/sauljabin/java-test-utils"></a>

This collection of functions add some interesting features for testing your code. It provides **Annotation assertions**
and **Reflection utils**.

### How to use it

Clone into your project:

```shell
git clone https://github.com/sauljabin/java-test-utils.git
```

Include it at `settings.gradle`:

```groovy
include('java-test-utils')
project(":java-test-utils").projectDir = file("java-test-utils/java-test-utils")
```

Add it as a dependency at `build.gradle`:

```groovy
dependencies {
    testImplementation project(':java-test-utils')
}
```

### Examples

```java
// Reflection on Field
DummyClass dummyClass=new DummyClass();
setFieldValue(dummyClass, FIELD_NAME, expectedValue);
assertThat(getFieldValue(dummyClass, FIELD_NAME)).isEqualTo(expectedValue);

// Refliction on static field
setStaticFieldValue(DummyClass.class, STATIC_FIELD_NAME, expectedValue);
assertThat(getStaticFieldValue(DummyClass.class, STATIC_FIELD_NAME)).isEqualTo(expectedValue);

// Assert class annotation
assertClassAnnotation(Dummy.class, DummyClassAnnotation.class);
assertClassAnnotationParameter(Dummy.class, DummyClassAnnotation.class, "value", "test_value");

// Assert field annotation
assertFieldAnnotation(Dummy.class, "dummy", DummyFieldAnnotation.class);
assertFieldAnnotationParameter(Dummy.class, "dummy", DummyFieldAnnotation.class, "value", "test_value");

// Assert method annotation
assertMethodAnnotation(Dummy.class, "dummyMethod", DummyMethodAnnotation.class);
assertMethodAnnotationParameter(Dummy.class, "dummyMethod", DummyMethodAnnotation.class, "value", "test_value");
```

### Development

Running tests:

```shell
./gradlew java-test-utils:test
```

Running demo tests:

```shell
./gradlew demo:test
```
