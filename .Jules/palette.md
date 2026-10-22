## 2023-10-24 - Numeric Keyboard Types added
**Learning:** In mobile UI frameworks like Jetpack Compose, numeric input fields (like salary, hours, or days) should configure `keyboardOptions` with a numeric `KeyboardType`. This vastly improves data entry UX compared to bringing up a full text keyboard.
**Action:** Always check numeric inputs for appropriate keyboard types in mobile UI enhancements.

## 2023-10-24 - Decorative icons and accessibility
**Learning:** In Jetpack Compose, setting `contentDescription` on decorative icons or icons next to descriptive text leads to redundant screen reader announcements (e.g. saying "Save Icon" and then "Save" text right after).
**Action:** Leave `contentDescription = null` for purely decorative icons to maintain a clean accessibility experience.
