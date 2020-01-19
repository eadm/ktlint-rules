# ktlint-rules
## General
Custom KtLint rules to check some of [code style rules](https://hackmd.io/@4F6roGkFSHeYx4R4sBs7ew/SyC1DwxNE)

Rules set was originaly developed as a module in https://github.com/StepicOrg/stepik-android repository.

#### Currently set consists of following rules:
- `explicit-public-type` &mdash; insists [explicit type on public methods](https://hackmd.io/@4F6roGkFSHeYx4R4sBs7ew/SyC1DwxNE#Types)
- `expression-body` *(with autocorrection)* &mdash; insists use of [expression body istead of return](https://hackmd.io/@4F6roGkFSHeYx4R4sBs7ew/SyC1DwxNE#Expression-body)
- `expression-body-indent` *(with autocorrection)* &mdash; tracks indent of expression body

## How to use
[ ![bintray](https://api.bintray.com/packages/eadm/ru.nobird.android/ru.nobird.android.ktlint/images/download.svg) ](https://bintray.com/eadm/ru.nobird.android/ru.nobird.android.ktlint)

Add following repository to access binaries 
```groovy=
allprojects {
    repositories {
        maven { url "https://dl.bintray.com/eadm/ru.nobird.android" }
```
