import os
from PIL import Image

def create_icons(source_image_path, output_dir):
    try:
        img = Image.open(source_image_path).convert("RGBA")
        width, height = img.size
        print(f"Original image size: {width}x{height}")
        
        # Calculate crop box. User wants "the area between the square with rounded corners"
        # The main icon box usually spans about 8-10% padding. Let's crop 10% from each side.
        margin_x = int(width * 0.10)
        margin_y = int(height * 0.10)
        
        left = margin_x
        top = margin_y
        right = width - margin_x
        bottom = height - margin_y
        
        cropped_img = img.crop((left, top, right, bottom))
        print(f"Cropped image size: {cropped_img.size}")
        
        # Sizes for mipmap
        sizes = {
            "mdpi": 48,
            "hdpi": 72,
            "xhdpi": 96,
            "xxhdpi": 144,
            "xxxhdpi": 192
        }
        
        for density, size in sizes.items():
            mipmap_dir = os.path.join(output_dir, f"mipmap-{density}")
            os.makedirs(mipmap_dir, exist_ok=True)
            
            resized = cropped_img.resize((size, size), Image.Resampling.LANCZOS)
            output_path = os.path.join(mipmap_dir, "ic_launcher.png")
            resized.save(output_path, format="PNG")
            print(f"Saved {output_path}")

    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    src = r"C:\Users\hp\.gemini\antigravity\brain\e8d43104-a92c-4204-96a8-01ba3f3ada18\logo_idea_3d_1775546906551.png"
    out = r"E:\Booking Management App\Android\app\src\main\res"
    create_icons(src, out)
