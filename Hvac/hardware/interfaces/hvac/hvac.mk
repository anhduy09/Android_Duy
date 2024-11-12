#$(call inherit-product, device/generic/car/emulator/aosp_car_emulator.mk)
#$(call inherit-product, $(SRC_TARGET_DIR)/product/sdk_x86_64.mk)


PRODUCT_PACKAGES += \
		vendor.duy.hvac \
		vendor.duy.hvac-service

VENDOR_PATH = vendor/duy/apps/Android_Duy/Hvac

#DEVICE_MANIFEST_FILE += \
#	$(VENDOR_PATH)/configs/device_manifest/manifest.xml

DEVICE_FRAMEWORK_COMPATIBILITY_MATRIX_FILE += \
	$(VENDOR_PATH)/configs/device_manifest/framework_compatibility_matrix.xml

BOARD_VENDOR_SEPOLICY_DIRS += \
	$(VENDOR_PATH)/sepolicy/vendor

#EMULATOR_VENDOR_NO_SOUND := true
#PRODUCT_NAME := aosp_car_x86_64
#PRODUCT_DEVICE := generic_car_x86_64
#PRODUCT_BRAND := DuyHvac
#PRODUCT_MODEL := DuyHvac on x86_64 emulator



