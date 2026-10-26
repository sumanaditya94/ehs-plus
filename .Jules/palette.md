## 2026-10-26 - Added KeyboardOptions for Number Inputs
**Learning:** In Jetpack Compose, text fields that expect numerical data (like salary, hours worked, days) will display a full QWERTY keyboard by default, which is frustrating for users entering lots of numbers.
**Action:** Always add `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)` or `KeyboardType.Decimal` to `OutlinedTextField` components when the expected input is purely numerical. This provides a much faster and more pleasant data entry experience.
