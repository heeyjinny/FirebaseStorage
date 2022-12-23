package com.heeyjinny.firebasestorage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.heeyjinny.firebasestorage.databinding.ActivityMainBinding

/**  파이어베이스 클라우드 스토리지 사용하기  **/

class MainActivity : AppCompatActivity() {

    //1
    //스토리지 연결하기
    //스토리지 연결 전 firebase-stprage의존성 추가 확인!!
    //스토리지 연결은 버킷이라고 불리는 주소로 함
    //버킷주소: 콘솔의 화면 중간 gs://로 시작하는 주소
    //버킷에 연결하는 코드 추가
    val storage = Firebase.storage(BuildConfig.STORAGE)

    //뷰바인딩
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }//onCreate

    //2
    //파이어베이스에 이미지 저장하는 메서드 작성
    //이미지 업로드 메서드 생성
    fun uploadImage(uri:Uri){
        //2-1
        //파일주소를 생성하는 메서드 변수 생성
        //파일주소: 경로 + 사용자아이디 + 밀리초
        val fullPath = makeFilePath("images", "temp", uri)
        //2-2
        //스토리지에 저장할 경로 설정
        val imageRef = storage.getReference(fullPath)
        //2-3
        //업로드 태스크 생성
        val uploadTask = imageRef.putFile(uri)

        //2-4
        //업로드 실행 및 결과 확인
        uploadTask.addOnFailureListener {
            Log.d("스토리지", "실패: ${it.message}")
        }.addOnSuccessListener {
            //2-5
            //성공 리스너를 통해 작업 성공 시
            //해당 경로를 저장해뒀다가 이미지를 불러올 때 사용함
            //이 프로젝트에서는 로그캣에 출력된 주소를 복사하여 사용...
            Log.d("스토리지", "성공주소: $fullPath")
        }

    }//uploadImage

    //3
    //파일 전체 경로를 생성하는 메서드 생성
    //경로 + 사용자아이디_시간값(밀리초) + 확장자 조합
    //사용자 아이디를 넣으면 스토리지 안 파일명의 중복을 방지할 수 있음
    //만약 로그인 기능이 없는 서비스라면
    //사용자아이디 대신 디바이스ID 또는 IP주소를 조합해 중복방지
    fun makeFilePath(path:String, userId:String, uri:Uri): String{
        //3-1
        //마임타입(ex. images/jpeg)으로 가져옴...
        //콘텐트 리졸버를 사용하여 확장자명 가져오기...
        //콘텐트 리졸버: 다른 앱에서 콘텐트 프로바이더를 통해 제공하는 데이터를 사용하기 위한 도구
        //엘비스연산자를 사용하여 기본 확장자 값 none으로 설정...
        val mimeType = contentResolver.getType(uri)?: "/none"
        //3-2
        //확장자만 변수에 저장하기
        val ext = mimeType.split("/")[1]

        //3-3
        //시간값(밀리초)
        val timeSuffix = System.currentTimeMillis()

        //3-4
        //파일이름 조함...
        //경로 + 사용자아이디_시간값(밀리초) + 확장자 조합
        val filename = "${path}/${userId}_${timeSuffix}.${ext}"

        return filename

    }//makeFilePath

}//MainActivity