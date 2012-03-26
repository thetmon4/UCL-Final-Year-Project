PayPal MPL Readme

=============
Release Notes
=============

version 1.5.5 (Build 1.5.5.44)
 - Improve airplane mode handling.  For maximum reliability, applications 
   should init the library after ensuring airplane mode is not enabled.

version 1.5.5 (Build 1.5.5.39)
 - Improved time of initialization call
 - Made initialization re-callable
 - Added better images for high resolution devices
 - Improved network handling and timeouts
 - Enabled per-applicationID "Remember Me" functionality
 - Add the ability for applications to cancel transactions during
   a payment adjustment.  See the method
   PaymentAdjusterDelegate.adjustAmountsAdvanced()

version 1.1.1 (Build 11.01.28.8321)
 - Disabled "Keep Me Logged In" functionality

version 1.1 (Build 10.12.09.8054)
 - Updated library to improve integration with developer applications
 - Updated documentation to include more information about preapproval key creation
 - Added interactive demo app to developer package
 - Updated localized strings for non-English languages
 - Updated error message handling

version 1.0 (build 10.10.22.6861)
 - Added support for split payments and chained payments
 - Added support for pre-approvals
 - Added use of Adaptive Payments back-end
 - Added support of auto-login features
 - Added support for PIN creation at the end of flow
 - Elminated in-library success page
 - Added improved scaling for different screen sizes

version 0.72 (build 10.7.26.4175)
 - Converted CheckoutButton to use text and image
 - Reduced compiled jar size by over 50%
 - Fixed Netherlands languages not being included
 - Added Austria (English) language
 - Fixed Chinese language buttons not displaying correctly
 - Fixed time out error not showing on login page
 - Updated documentation to reflect fixed languages and new button type

version 0.72 (build 10.7.15.3666)
 - Fixed crash in example Pizza application due to invalid spinner index
 - Fixed crash on Pizza app results screen if orientation was changed
 - Fixed Pizza app results screen not displaying correctly in landscape mode
 - Added a results page to Pizza app with PayPal result details
 - Corrected minor typo in example Pizza application popup
 - Corrected example QA application code for proper initialization
 - Noted minor bug due to race condition if using multithreading loading
 - Added documentation noting potential load times on older devices

version 0.72 (build 10.7.12.3477)

 - Fixed being able to quickpay with a delivery in example Pizza application
 - Fixed amount changing to zero and crashing due to orientation changing:
 - Added an exception thrown if the library is initialized more than once
 - Added documentation noting required attributes in the manifest
 - Added documentation noting correct initialization to avoid exceptions

version 0.72 (build 10.7.7.3385)

 - Initial release of Readme file
 - Shipping and tax estimation note removed from review screen
 - Removed auto-completion from email, password, phone and pin fields
 - Pressing back after payment is done returns PAYMENT_SUCEEDED
 - Added red developer stripe when on sandbox or none server
 - Added version and build to help screen on library UI
 - Added getVersion() and getBuild() functions to return strings as needed
